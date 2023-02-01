#include "jni.h"
#include <stdio.h>
#include "SunnyMilkFuzzer.h"

JNIEXPORT void JNICALL Java_SunnyMilkFuzzer_PrintCoverage
  (JNIEnv * env, jobject o) {
    int* coverage_table = (*env)->GetSunnyMilkFuzzerCoverage();
    for (int i = 0; i < 10; ++i) {
        printf("%d ",coverage_table[i]);
    }
    printf("\n");
    return;
}