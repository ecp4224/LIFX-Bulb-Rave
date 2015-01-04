#include <stdio.h>
#include <bass.h>
#include <basswasapi.h>
#include <jni.h>
#include <me_eddiep_lifxbulb_rave_system_WASAPI.h>

// WASAPI function
DWORD CALLBACK WasapiProc(void *buffer, DWORD length, void *user)
{
	return TRUE; //We're not doing anything..
}

int devnum = -1;
jboolean JNICALL Java_me_eddiep_lifxbulb_rave_system_WASAPI_loadWrapper(JNIEnv *env, jobject thiz) {
	BASS_WASAPI_DEVICEINFO info;

	for (int a = 0; BASS_WASAPI_GetDeviceInfo(a, &info); a++) {
		if (!(info.flags&BASS_DEVICE_INPUT) && (info.flags&BASS_DEVICE_DEFAULT)) {
			devnum = a + 1;
			break;
		}
	}

	BASS_Init(0, 44100, 0, NULL, NULL);

	if (devnum >= 0) {
		if (!BASS_WASAPI_Init(devnum, 0, 0, BASS_WASAPI_BUFFER, 1.0, 0, WasapiProc, NULL)) {
			return JNI_FALSE;
		}
		BASS_WASAPI_Start();
	}

	return JNI_TRUE;
}

jfloatArray JNICALL Java_me_eddiep_lifxbulb_rave_system_WASAPI_getSoundData(JNIEnv *env, jobject thiz) {
	float fft[1024] = { 0 };
	BASS_WASAPI_GetData(fft, BASS_DATA_FFT2048);

	jfloatArray result;
	result = env->NewFloatArray(1024);
	env->SetFloatArrayRegion(result, 0, 1024, fft);

	//free(fft); this causes the JVM to crash...

	return result;
}
