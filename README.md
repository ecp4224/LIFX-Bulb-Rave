LIFX-Bulb-Rave
==============

Create a rave party using LIFX bulbs!

This simple console application captures the sound coming from your speakers and creates a rave party with the LIFX-Bulb's specified!

Since the [WASAPI (Windows Audio Session API)](http://msdn.microsoft.com/en-us/library/windows/desktop/dd371455%28v=vs.85%29.aspx) is only avilable on windows, this program will only work on windows :/ (for now)

#TODO
* Test current version
* Make version for OSX
* Make version for linux

#Compile

To compile the java code, you need the following libraries:
* [lifx-sdk-java](https://github.com/besherman/lifx-sdk-java)

To compile the C++ code, you need the following libraries:
* [Java JDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Un4seen BASS](http://www.un4seen.com/bass.html)
* [WASAPI add-on](http://www.un4seen.com/bass.html)

#License
```
Copyright (c) 2015 Eddie Penta

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
```
