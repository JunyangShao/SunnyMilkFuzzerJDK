#include <stdio.h>
#include <jni.h>
#include <cstdint>
#include "FuzzerDefs.h"
#include "FuzzerPlatform.h"
#include "FuzzerTracePC.h"

JNIEnv *env;
jclass cls;
jmethodID fuzz_one_method;

extern "C" int LLVMFuzzerTestOneInput(const uint8_t *Data, size_t Size) {
    // Convert the uint8_t array to a jchar array
    jchar *jcharData = new jchar[Size];
    for (size_t i = 0; i < Size; ++i) {
        jcharData[i] = static_cast<jchar>(Data[i]);
    }

    // Create a jstring from the jchar array and the given size
    jstring input_str = env->NewString(jcharData, Size);

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

    // Randomly set the coverage counters
    // uint8_t* ctrs = env->GetSunnyMilkFuzzerCoverage();
    // static int iii = 0;
    // static int jjj = 0;
    // if (iii++ < 100) {
    //     ctrs[(jjj++) & 0xFFFF] = 1;
    // }

    return 0; // Return a value to match the int return type
}

int main(int argc, char *argv[]) {
    if (argc < 3) {
        fprintf(stderr, "Usage: %s [libFuzzerArguments]* <JavaClassName>\n", argv[0]);
        return 1;
    }

    JavaVM *jvm;
    JavaVMInitArgs vm_args;
    JavaVMOption options[5];

    options[0].optionString = "-Djava.class.path=./:./fastjson-1.2.75.jar"; // Set classpath here
    // options[1].optionString = "-XX:TieredStopAtLevel=1";
    options[1].optionString = "-XX:+UseParallelGC";
    options[2].optionString = "-XX:+CriticalJNINatives";
    options[3].optionString = "-Xmx1800m";
    
    // options[2].optionString = "-XX:CICompilerCount=1";
    // options[2].optionString = "-XX:+UnlockDiagnosticVMOptions";
    // options[3].optionString = "-XX:+PrintAssembly";
    // options[4].optionString = "-XX:+PrintCompilation";
    // options[1].optionString = "-Xint";
    vm_args.version = JNI_VERSION_1_8;
    vm_args.options = options;
    vm_args.nOptions = 4;
    vm_args.ignoreUnrecognized = JNI_FALSE;

    jint res = JNI_CreateJavaVM(&jvm, (void**)&env, &vm_args);
    if (res != JNI_OK) {
        fprintf(stderr, "Error: Failed to create JVM. Error code: %d\n", res);
        return 1;
    }

    uint8_t* ctrs = env->GetSunnyMilkFuzzerCoverage();
    fuzzer::TPC.HandleInline8bitCountersInit(ctrs, ctrs + (1 << 16));
    env->ClearSMFTable();
    if (((uintptr_t)ctrs & 4096u != 0)) {
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

    jmethodID fuzzer_init_method = env->GetStaticMethodID(cls, "FuzzerInit", "()V");
    if (fuzzer_init_method != NULL) {
        env->CallStaticVoidMethod(cls, fuzzer_init_method);
    }
    env->ExceptionClear();

    --argc;
    int ret = fuzzer::FuzzerDriver(&argc, &argv, LLVMFuzzerTestOneInput);
    
    jvm->DestroyJavaVM();

    return 0;
}