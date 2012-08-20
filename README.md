
== Spectral analyzer for android

This small program allows you to play around with accelerometer and perform spectral analyze of
movements of your phone. Start activity shows waterfall display of current spectrogram. (amplitude
shows energy in certain area and color codes phase) Just let  you phone on the table, wait till the buffers
are  full and startup artifacts disappear -  then hit the table and watch what happens.

You can also record longer sample for later display  -  start recording with toggle button,  and then go to jog,
ride you bike or car - whatever you like (I recorded over 2 hours apiece and it took about 15 MB on SD Card).  Select
sample for display via menu.

=== Compiling this application

This application has maven build, but you will certainly lack some artifacts which are not yet on maven central.
Those are my opensourced utility libraries available on github:

  - accmeter ( convenience library to work with accelerometer on android:  https://github.com/ko5tik/accmeter )
  - andject ( Lightweight dependency injection for android: https://github.com/ko5tik/andject )
  - jsonmarshaller ( Lightweight databinding for JSON on android:  https://github.com/ko5tik/jsonserializer )


Have fun,  clone it, drop me a line and make push requests.
