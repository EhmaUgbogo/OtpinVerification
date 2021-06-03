![GitHub release (latest by date)](https://img.shields.io/github/v/release/EhmaUgbogo/OtpVerificationLib?style=flat-square) ![GitHub top language](https://img.shields.io/github/languages/top/EhmaUgbogo/OtpVerificationLib?style=flat-square) ![GitHub all releases](https://img.shields.io/github/downloads/EhmaUgbogo/OtpVerificationLib/total) ![Twitter Follow](https://img.shields.io/twitter/follow/EhmaUgbogo?style=social)

# OtpinVerification 

> OtpinVerification & Dialog

### Description:
Extremely useful library for validating EditText inputs whether by using just the validator (OtpinVerification) for your custom view or using library's extremely resizable & customisable dialog (OtpinDialogCreator)

___

### Features:
- OtpinVerification for validating your EditTexts from your own layout view
- OtpinDialogCreator (library's customisable dialog)

___

### Setup. 
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
    implementation 'com.github.EhmaUgbogo:OtpinVerification:1.0'
}
```
___

### Usage

1. OtpinVerification

```kotlin
  
  val yourEditTexts = listOf(yourEditText1, yourEditText2, yourEditText3,...)
  val inputType = OtpInputType.DIGIT // Library default. if used can be ommited
  
  otpin = OtpinVerification(yourEditTexts, inputType) { validated, percent, otp ->
      // Your code here
  }
  
  
  // SetText at runtime
  otpin.setOtpInputsText(yourOtpText)
  
  // Clear edittexts at runtime
  otpin.clearInputs()
  
  
  // Add count down listener
  
  otpin.startCountDown(minutes, object :OtpCountDownListener{
      override fun onCountDown(sec: Int, minutes: Int, timeFormat: String, onFinish: Boolean) {
            // your code here
            if(onFinish){
                // your code here
            }
      }
 })
  
  
  
```

2. OtpinDialogCreator

```kotlin
  
  // Operations that can be done on the dialog
  
  otpDialog = OtpinDialogCreator.with(this)
            .title(title)
            .customSubtitle("Please provide your card cvv")
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
            //.theme(R.style.OtpStyle)
            //.windowAnimation(R.style.yourWindowStyle)
            //.disableWindowAnimation()
            //.boxShape() // Use styles instead // Not available via runtime now
            .start()
 
  
    // SetText at runtime
    otpDialog.setOtpInputsText(yourOtpText)
    
    // dismiss OtpinDialog
    otpDialog.dismissDialog()
    
    // Show message via SnackBar or Toast
    otpDialog.showMessage(msg, useSnackInsteadOfToast = true)
    
    // Hide progressBar
    otpDialog.hideProgress()
  
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
    <item name="otpDialogButtonStyle">@style/OtpDialogButtonStyle.Round</item>
    <!-- Toolbar -->
    <item name="otpDialogToolbarColor">@color/otp_color_toolbar</item>
    <item name="otpDialogToolbarIconColor">@color/otp_color_toolbar_icon</item>
    <!-- Layout -->
    <item name="otpDialogLayoutPadding">4dp</item>
    <item name="otpDialogLayoutCornerRadius">16dp</item>
  
```


### Styling Yours
So to style yours simply extend any of the styles above or supply yours.

for example to change the title font & size, see yourTitleAppearance below.


```xml

    <style name="AppTheme" parent="Theme.MaterialComponents.DayNight.DarkActionBar">
        <!-- Primary brand color. -->
        <item name="colorPrimary">@color/purple_500</item>
        ...
        <!-- Customize OtpinDialog here like so. -->
        <item name="otpDialogTitleTextAppearance">@style/yourTitleAppearance</item>
        <item name="otpDialogBoxSpacing">12dp</item>
        <!-- Etc, Etc -->
    </style>


    <style name="yourTitleAppearance" parent="OtpDialogTitleAppearance">
        <item name="android:textSize">22sp</item>
        <item name="fontFamily">@font/montserrat_black</item>
    </style>
  
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

