# TakingPhotosSimply
This is a demo of Android training [Taking Photos Simply](https://developer.android.com/training/camera/photobasics.html). You get complete code from this demo App so you can learn how to take photos simply in Android.

## How to?
- Only get a thumbnail (test 1)
 - Start camera Activity with Intent of action `ACTION_IMAGE_CAPTURE`.
 - Get thumbnail bitmap from `data`'s extra in `onActivityResult()`.
- Save the full-size photo to private storage (test 2)
 - Request `WRITE_EXTERNAL_STORAGE` permission on Android 4.3 and lower. You're good to go without this permission while on Android 4.4 and above.
 - Declare a FileProvider pointed to private storage.
 - Use `getExternalFilesDir(Environment.DIRECTORY_PICTURES)` to create a File to save the photo.
 - Start camera Activity with Intent of action `ACTION_IMAGE_CAPTURE` and `MediaStore.EXTRA_OUTPUT` extra.
 - Decode the bitmap in `onActivityResult()`.
- Save the full-size photo to public storage and add it to gallery (test 3)
 - Request `WRITE_EXTERNAL_STORAGE` permission.
 - Declare a FileProvider pointed to public storage.
 - Use `getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)` to create a File to save the photo.
 - Decode the bitmap in `onActivityResult()`.
 - Send a broadcast to invoke the system's media scanner to add the photo to the Media Provider's database.
- Save the full-size photo to public storage and compress it (test 4)
 - Every step is the same as test 3 except changing the last step to compressing bitmap.
 
## License

    Copyright 2017 gejiaheng

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
