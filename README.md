![GitHub release (latest by date)](https://img.shields.io/github/v/release/EhmaUgbogo/OtpinVerification?style=flat-square) ![GitHub top language](https://img.shields.io/github/languages/top/EhmaUgbogo/OtpinVerification?style=flat-square) ![GitHub all releases](https://img.shields.io/github/downloads/EhmaUgbogo/OtpinVerification/total) ![Twitter Follow](https://img.shields.io/twitter/follow/EhmaUgbogo?style=social)

# OtpinVerification 

> OtpinVerification & Dialog

## Description:
Extremely useful library for validating EditText inputs whether by using just the validator (OtpinVerification) for your custom view or using library's extremely resizable & customisable dialog (OtpinDialogCreator)

___

## Features:
- OtpinVerification for validating your EditTexts from your own layout view
- OtpinDialogCreator (library's customisable dialog)

___

## Setup. 
To get a Git project into your build: 

1. Add the JitPack repository to you root build.gradle at the end of repositories:

```groovy
allprojects {
    repositories {
	...
	maven { url 'https://jitpack.io' }
    }
}
```

2. Add the dependency

```groovy
dependencies {
    implementation 'com.github.EhmaUgbogo:OtpinVerification:1.0.0'
}
```
___

## Usage

### 1. OtpinVerification (Use with your own custom layout view)

```kotlin
  
  val yourEditTexts = listOf(yourEditText1, yourEditText2, yourEditText3,...)
  val inputType = OtpInputType.DIGIT // Library default. if used can be ommited
  
  otpin = OtpinVerification(yourEditTexts, inputType) { validated, percent, otp ->
      // Your code here
  }//.startCountDown(5, countDownListener)
  
  
  otpin.setOtpInputsText(yourOtpText) //SetText at runtime
  
  otpin.clearInputs() //Clear edittexts at runtime
  
```


   Add CountDown
```kotlin

 otpin.startCountDown(minutes, object :OtpCountDownListener{
      override fun onCountDown(sec: Int, minutes: Int, timeFormat: String, onFinish: Boolean) {
            // your code here
            if(onFinish){
                // your code here
            }
      }
 })
 
```









### 2. OtpinDialogCreator

![Image Sample1](https://drive.google.com/file/d/1HUDFv9vAqF4empGn9VHoIDIJXo9rrFJ_/view?usp=drivesdk)


```kotlin
  
  // All operations that can be done on the dialog
  
  otpDialog = OtpinDialogCreator.with(this)
            //.title(title)
            //.customSubtitle("Please provide your card cvv")
            .customBtnText("Submit")
            //.logo(R.drawable.ic_logo)
            .cancelable(false)
            .otpFields(OtpFields.THREE)
            .inputType(OtpInputType.DIGIT)
            .countDown(5)
            .setCountDownFinishListener { showToast("Count Down completed") }
            .setContinueListener { otpDialog, otp -> continueClicked(otpDialog, otp) }
            .setResendListener {resendClicked(it)}
            .setCancelListener { showToast("Task Cancelled") }
            .displayMode(OtpDisplay.FLOAT)
            //.excludeResend()
            //.displayOnlyInputFields()
            //.theme(R.style.myOtpDialogTheme)
            //.windowAnimation(R.style.yourWindowStyle)
            //.disableWindowAnimation()
            //.boxShape() // Use styles instead // Not available via runtime now
            .start()
	    
	    
	   otpDialog.setOtpInputsText(yourOtpText) //SetText at runtime
	   
	   otpDialog.dismissDialog() //Dismiss OtpinDialog
	   
	   otpDialog.showMessage(msg, useSnackInsteadOfToast = true) //Show message via SnackBar or Toast
	   
	   otpDialog.hideProgress() // Hide otpDialog progressBar
 
  
```




### Styling Used Underneath
OtpinDialogCreator uses the following attr and styles under the hood with values that you can easily adjut to suit your brand

```xml
    <!-- TextAppearance-->
    <item name="otpDialogTitleTextAppearance">@style/OtpDialogTitleAppearance</item>
    <item name="otpDialogSubtitleTextAppearance">@style/OtpDialogSubTitleAppearance</item>
    <item name="otpDialogCounterTextAppearance">@style/OtpDialogCounterTextAppearance</item>
    <item name="otpDialogResendTextAppearance">@style/OtpDialogResendTextAppearance</item>
    <!-- Box Setup -->
    <item name="otpDialogBoxWidth">48dp</item>
    <item name="otpDialogBoxHeight">48dp</item>
    <item name="otpDialogBoxStrokeWidth">1dp</item>
    <item name="otpDialogBoxStrokeWidthFocused">2dp</item>
    <item name="otpDialogBoxSpacing">8dp</item>
    <item name="otpDialogBoxStyle">@style/BoxStyle.Outline</item> <!-- BoxStyle.Outline, BoxStyle.Underline-->
    <!-- Box ShapeAppearance-->
    <item name="otpDialogBoxShape">@style/OtpBoxShape.Normal</item> <!--Normal, Rounded, Cut - Only works for BoxStyle.Outline-->
    <!-- Box EditText Style-->
    <item name="otpDialogBoxEditTextStyle">@style/BoxEditTextStyle</item>
    <!-- Button Style-->
    <item name="otpDialogButtonStyle">@style/OtpDialogButtonStyle.Round</item> <!--OtpDialogButtonStyle, OtpDialogButtonStyle.Round -->
    <!-- Toolbar -->
    <item name="otpDialogToolbarColor">@color/otp_color_toolbar</item>
    <item name="otpDialogToolbarIconColor">@color/otp_color_toolbar_icon</item>
    <!-- Layout -->
    <item name="otpDialogLayoutPadding">4dp</item>
    <item name="otpDialogLayoutCornerRadius">16dp</item>
  
```


### Styling Yours
So to style yours simply create a style that extends otpDialogTheme then use any precreated styles or extend them for further customization.
for example to change the title font & size, see yourTitleAppearance below.


```xml

    <!-- your otpDialog theme here -->
    <style name="myOtpDialogTheme" parent="OtpDialogTheme">
	 <!-- precreated choice -->
        <item name="otpDialogBoxStyle">@style/BoxStyle.Underline</item>
        <item name="otpDialogBoxShape">@style/OtpBoxShape.Rounded</item>
        <item name="otpDialogButtonStyle">@style/OtpDialogButtonStyle</item>
	<item name="otpDialogBoxSpacing">12dp</item>

	    <!-- Customize like so. -->
        <item name="otpDialogTitleTextAppearance">@style/yourTitleAppearance</item>
        
    </style>

    <style name="yourTitleAppearance" parent="OtpDialogTitleAppearance">
        <item name="android:textSize">22sp</item>
        <item name="fontFamily">@font/montserrat_black</item>
    </style>
  
```

```kotlin
  // Apply your theme
  otpDialog = OtpinDialogCreator.with(this)
            .title(title)
            .theme(R.style.myOtpDialogTheme)
            .start()
  
```

### Color
For colors add these below to your color.xml

```xml

    <color name="otp_color_status_bar">@color/your_color_here</color>
    <color name="otp_color_toolbar">@color/your_color_here</color>
    <color name="otp_color_toolbar_icon">@color/your_color_here</color>
    <color name="otp_color_primary">@color/your_color_here</color>
    <color name="otp_color_widget_active">@color/your_color_here</color>
    <color name="otp_title_color">@color/your_color_here</color>
    <color name="otp_subtitle_color">@color/your_color_here</color>
  
```
    





### Licence

MIT Licence

Copyright (c) [2021] Ehma Ugbogo

Permission is hereby granted, free of charge, to any person obtaining
a copy of this software and associated documentation files (the
"Software"), to deal in the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish,
distribute, sublicense, and/or sell copies of the Software, and to
permit persons to whom the Software is furnished to do so, subject to
the following conditions:

The above copyright notice and this permission notice shall be
included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

___

### Author Info

[@EhmaUgbogo](https://twitter.com/EhmaUgbogo)


[Back To The Top](#otpinverification)

