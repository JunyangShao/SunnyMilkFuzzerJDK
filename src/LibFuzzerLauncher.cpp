#include <stdio.h>
#include <jni.h>
#include <cstdint>
#include "FuzzerDefs.h"
#include "FuzzerPlatform.h"
#include "FuzzerTracePC.h"
#include "FuzzerCorpus.h"
#include <iostream>

JNIEnv *env;
jclass cls;
jmethodID fuzz_one_method;
int smf_cov_map_size = 4096;
int smf_method_number = 0;
uint8_t* smf_cov_map = NULL;
int*     smf_method_size_table = NULL;
uint8_t* smf_method_hit_table = NULL;

void SetLibFuzzerFeatureMap(uint32_t * the_map) {
    env->SetLibFuzzerFeatureMap(the_map);
}

extern "C" int LLVMFuzzerTestOneInput(const uint8_t *Data, size_t Size) {
    char *jcharData = new char[Size + 1];
    memcpy(jcharData, Data, Size);
    jcharData[Size] = '\0';

    // Create a jstring from the C string
    jstring input_str = env->NewStringUTF(jcharData);

    // Call the Java method with the created jstring
    env->SetSMFBegin();
    env->CallStaticVoidMethod(cls, fuzz_one_method, input_str);
    env->UnsetSMFBegin();
    
    // Check for exceptions
    if (env->ExceptionOccurred()) {
        env->ExceptionDescribe();
        abort();
    }

    // Clean up the jchar array
    delete[] jcharData;
    env->DeleteLocalRef(input_str);

    // Check if the coverage map has been updated
    int new_smf_cov_map_size = env->GetSunnyMilkFuzzerCoverageSize();
    if (new_smf_cov_map_size != smf_cov_map_size) {
        fuzzer::TPC.HandleInline8bitCountersInit(
            smf_cov_map + smf_cov_map_size,
            smf_cov_map + new_smf_cov_map_size);
        smf_cov_map_size = new_smf_cov_map_size;
    }
    int new_smf_method_number = env->GetSunnyMilkFuzzerMethodNumber();
    if (new_smf_method_number != smf_method_number) {
        smf_method_number = new_smf_method_number;
        fuzzer::TPC.HandleMethodTablesInit(
            smf_method_size_table,
            smf_method_hit_table,
            smf_method_number);
        // printf("[SMF]\t Now printing the method size table...\n");
        // for (int i = 0; i < smf_method_number; i++) {
        //     printf("%d\t", smf_method_size_table[i]);
        // }
        // printf("\n[SMF]\t Now printing the method hit table...\n");
        // for (int i = 0; i < smf_method_number; i++) {
        //     printf("%d\t", smf_method_hit_table[i]);
        // }
        // printf("\n[SMF]\t Now printing the map for each hit method...\n");
        // for (int i = 0; i < smf_method_number; i++) {
        //     if (smf_method_hit_table[i] == 0) {
        //         continue;
        //     }
        //     for (int j = 0; j < smf_method_size_table[i]; j++) {
        //         printf("%d ", smf_cov_map[j]);
        //     }
        //     printf("\n");
        //     smf_cov_map += smf_method_size_table[i];
        // }
    }

    // uint32_t smf_total_counted = 0;
    // uint32_t smf_total_metod_size = 0;
    // // count the total size of activated methods
    // for (int i = 0; i < smf_method_number; i++) {
    //     if (smf_method_hit_table[i] != 0) {
    //         smf_total_counted += smf_method_size_table[i];
    //     }
    //     smf_total_metod_size += smf_method_size_table[i];
    // }
    // std::cout << "[SMF]\t smf_total_counted: " << smf_total_counted << std::endl;
    // std::cout << "[SMF]\t smf_method_number: " << smf_method_number << std::endl;
    // std::cout << "[SMF]\t smf_cov_map_size: " << smf_cov_map_size << std::endl;
    // std::cout << "[SMF]\t smf_total_metod_size: " << smf_total_metod_size << std::endl;

    return 0; // Return a value to match the int return type
}

int main(int argc, char *argv[]) {
    if (argc < 3) {
        fprintf(stderr, "Usage: %s [libFuzzerArguments]* <JavaClassName> ---p=[JarPath]\n", argv[0]);
        return 1;
    }

    JavaVM *jvm;
    JavaVMInitArgs vm_args;
    JavaVMOption options[5];
    char class_path[1024] = "-Djava.class.path=./:";
    int last_arg_len = strlen(argv[argc - 1]);
    int smf_arg_cnt = 1;
    if (last_arg_len > 5 && strncmp(argv[argc - 1], "---p=", 5) == 0) {
        int orig_len = strlen(class_path);
        strncpy(class_path + orig_len, argv[argc - 1] + 5, last_arg_len - 5);
        class_path[orig_len + last_arg_len - 5] = '\0';
        smf_arg_cnt = 2;
    }

    options[0].optionString = class_path;
    // options[1].optionString = "-XX:TieredStopAtLevel=1";
    // options[1].optionString = "-XX:+UseParallelGC";
    // options[2].optionString = "-XX:+CriticalJNINatives";
    // options[3].optionString = "-Xmx1800m";
    
    // options[2].optionString = "-XX:CICompilerCount=1";
    // options[2].optionString = "-XX:+UnlockDiagnosticVMOptions";
    // options[3].optionString = "-XX:+PrintAssembly";
    // options[4].optionString = "-XX:+PrintCompilation";
    // options[4].optionString = "-Xint";
    vm_args.version = JNI_VERSION_1_8;
    vm_args.options = options;
    vm_args.nOptions = 2;
    vm_args.ignoreUnrecognized = JNI_FALSE;

    jint res = JNI_CreateJavaVM(&jvm, (void**)&env, &vm_args);
    if (res != JNI_OK) {
        fprintf(stderr, "Error: Failed to create JVM. Error code: %d\n", res);
        return 1;
    }

    smf_cov_map = env->GetSunnyMilkFuzzerCoverage();
    fuzzer::TPC.HandleInline8bitCountersInit(smf_cov_map, smf_cov_map + smf_cov_map_size);
    env->ClearSMFTable();
    smf_method_size_table = env->GetSunnyMilkFuzzerMethodSizeTable();
    smf_method_hit_table = env->GetSunnyMilkFuzzerMethodHitTable();
    fuzzer::TPC.HandleMethodTablesInit(
        smf_method_size_table,
        smf_method_hit_table,
        smf_method_number);
    fuzzer::SetSetGloablFeatureMap(SetLibFuzzerFeatureMap);
    if (((uintptr_t)smf_cov_map & 4096u != 0)) {
        printf("Coverage map is not aligned!\n");
        return 1;
    }

    const char *class_name = argv[2];
    cls = env->FindClass(class_name);
    if (cls == NULL) {
        fprintf(stderr, "Error: Failed to find class '%s'\n", class_name);
        return 1;
    }

    // Find the FuzzOne method in the class
    fuzz_one_method = env->GetStaticMethodID(cls, "FuzzOne", "(Ljava/lang/String;)V");
    if (fuzz_one_method == NULL) {
        fprintf(stderr, "Error: Failed to find method FuzzOne\n");
        return 1;
    }
    // Create a jstring object for the input string "Hello World"

    jmethodID fuzzer_init_method = env->GetStaticMethodID(cls, "fuzzerInitialize", "()V");
    if (fuzzer_init_method != NULL) {
        env->CallStaticVoidMethod(cls, fuzzer_init_method);
    }
    env->ExceptionClear();

    argc -= smf_arg_cnt;
    int ret = fuzzer::FuzzerDriver(&argc, &argv, LLVMFuzzerTestOneInput);
    
    jvm->DestroyJavaVM();

    return 0;
}