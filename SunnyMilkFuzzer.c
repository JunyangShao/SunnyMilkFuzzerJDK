#include "jni.h"
#include <stdio.h>
#include "SunnyMilkFuzzer.h"
#include <string.h>
#include <stdlib.h>
#include <time.h>

unsigned char SMF_table[1 << 16];

JNIEXPORT jboolean JNICALL Java_SunnyMilkFuzzer_HasNewCoverage
  (JNIEnv * env, jobject o) {
    int ret = 0;
    unsigned char* coverage_table = (*env)->GetSunnyMilkFuzzerCoverage();
    for (int i = 0; i < (1 << 16); ++i) {
        if(coverage_table[i] == 1 && SMF_table[i] == 0) {
            SMF_table[i] = 1;
            ret = 1;
        }
    }
    return (jboolean)ret;
}

JNIEXPORT void JNICALL Java_SunnyMilkFuzzer_SetCoverageTracing
  (JNIEnv * env, jobject o) {
    (*env)->SetSMFBegin();
    return;
}

JNIEXPORT void JNICALL Java_SunnyMilkFuzzer_UnsetCoverageTracing
  (JNIEnv * env, jobject o) {
    (*env)->UnsetSMFBegin();
    return;
}

JNIEXPORT void JNICALL Java_SunnyMilkFuzzer_ClearCoverageMap
  (JNIEnv * env, jobject o){
    time_t t;
    srand((unsigned) time(&t));
    (*env)->ClearSMFTable();
    memset(SMF_table, 0, (1<<16) * sizeof(unsigned char));
    return;
}

int Rand(int x) {
  if (x == 0) return 0;
  return rand() % x;
}

char RandCh(int x) {
  if (x & 1)
    return Rand(256);
  static const char Special[] = "!*'();:@&=+$,/?%#[]012Az-`~.\xff\x00";
  return Special[Rand(sizeof(Special) - 1)];
}

size_t Mutate_EraseBytes(char *Data, size_t Size, size_t MaxSize) {
  if (Size <= 1) return 0;
  size_t N = Rand(Size / 2) + 1;
  if(N >= Size) return 0;
  size_t Idx = Rand(Size - N + 1);
  // Erase Data[Idx:Idx+N].
  memmove(Data + Idx, Data + Idx + N, Size - Idx - N);
  // Printf("Erase: %zd %zd => %zd; Idx %zd\n", N, Size, Size - N, Idx);
  return Size - N;
}

size_t Mutate_InsertByte(char *Data, size_t Size, size_t MaxSize) {
  if (Size >= MaxSize) return 0;
  size_t Idx = Rand(Size + 1);
  // Insert new value at Data[Idx].
  memmove(Data + Idx + 1, Data + Idx, Size - Idx);
  Data[Idx] = RandCh(rand());
  return Size + 1;
}

size_t Mutate_InsertRepeatedBytes(char *Data, size_t Size, size_t MaxSize) {
  const size_t kMinBytesToInsert = 3;
  if (Size + kMinBytesToInsert >= MaxSize) return 0;
  size_t MaxBytesToInsert = MaxSize - Size < 128 ? MaxSize - Size : 128;
  size_t N = Rand(MaxBytesToInsert - kMinBytesToInsert + 1) + kMinBytesToInsert;
  if(!(Size + N <= MaxSize && N)) return 0;
  size_t Idx = Rand(Size + 1);
  // Insert new values at Data[Idx].
  memmove(Data + Idx + N, Data + Idx, Size - Idx);
  // Give preference to 0x00 and 0xff.
  char Byte = rand() & 1 ? Rand(256) : (rand() & 1 ? 0 : 255);
  for (size_t i = 0; i < N; i++)
    Data[Idx + i] = Byte;
  return Size + N;
}

size_t Mutate_ChangeByte(char *Data, size_t Size, size_t MaxSize) {
  if (Size > MaxSize) return 0;
  size_t Idx = Rand(Size);
  Data[Idx] = RandCh(rand());
  return Size;
}

size_t Mutate_ChangeBit(char *Data, size_t Size, size_t MaxSize) {
  if (Size > MaxSize) return 0;
  size_t Idx = Rand(Size);
  Data[Idx] ^= 1 << Rand(8);
  return Size;
}

typedef size_t (*Mutator)(char*, size_t, size_t);
Mutator mutators[] = { Mutate_EraseBytes, Mutate_InsertByte,
                       Mutate_InsertRepeatedBytes, Mutate_ChangeByte,
                       Mutate_ChangeBit};
#define MAX_SIZE 20

JNIEXPORT jstring JNICALL Java_SunnyMilkFuzzer_Mutate
  (JNIEnv * env, jobject obj, jstring str, jint ssize) {
    const char *input = (*env)->GetStringUTFChars(env, str, NULL);
    char *output = (char *)malloc(MAX_SIZE);
    memcpy(output, input, ssize);
    output[ssize] = '\0';

    while(!mutators[Rand(5)](output, ssize, MAX_SIZE));
    output[MAX_SIZE - 1] = '\0';

    (*env)->ReleaseStringUTFChars(env, str, input);

    return (*env)->NewStringUTF(env, output);
}