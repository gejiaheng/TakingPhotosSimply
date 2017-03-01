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
