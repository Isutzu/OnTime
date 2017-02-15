# App Name - *OnTime*

**OnTime** is a simple alarm application for Android.
At work I have 30min for taking lunch so I use this app to keep track of the time
The alarm is set up to fire by default 30min after the activation but this time can be changed
and use it as a general purpose alarm.(this is a working progress feature)

 :floppy_disk: [Download apk](https://github.com/Isutzu/OnTime/blob/master/OnTime.apk?raw=true) &nbsp;&nbsp;&nbsp;&nbsp;
 :iphone:[Test App](https://appetize.io/embed/e03u41zbbamge6t9urecaredy4?device=nexus5&scale=65&orientation=portrait&osVersion=7.0)

## Usage

* Just press the -- start lunch -- button and the alarm will fire up 30min after.
* A notification is received when the alarm is activated which can be stopped it by pressing the
action button.


The following **additional** features will be implemented:

- [ ] Pick the alarm sound
- [x] The ability to change the default 30min time by using a TimePicker dialog
- [ ] Implementation of material design
- [ ] Adding a shortcut to the tiles bar for a quick access
- [ ] Improving user interface and user experience.

## Video Walkthrough


<img src='http://i.imgur.com/Oa4Opi9.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

For demonstration the alarm have been set to fire up after 10 seconds

GIF created with [LiceCap](http://www.cockos.com/licecap/).


## Notes

How to check if AlarmManager already has an alarm set?
```java
boolean alarmUp = (PendingIntent.getBroadcast(context, 0,
        new Intent("com.my.package.MY_UNIQUE_ACTION"),
        PendingIntent.FLAG_NO_CREATE) != null);

if (alarmUp)
{
    Log.d("myTag", "Alarm is already active");
}
```

## Resources

- [check if an alarm is already set up ](http://stackoverflow.com/questions/4556670/how-to-check-if-alarmmanager-already-has-an-alarm-set)
- [Formatting Date and Time](http://stackoverflow.com/questions/2271131/display-the-current-time-and-date-in-an-android-application)
- [custom TimePicker Dialog](https://android--examples.blogspot.com/2015/04/timepickerdialog-in-android.html)
- [Fixing TimePickerDialog Theme on Nougat 7.0](https://gist.github.com/jeffdgr8/6bc5f990bf0c13a7334ce385d482af9f)

## To Read
- [custom TimePicker Dialog. Adjusting the minutes in intervals](https://github.com/ziaagikian/Custom-Time-Picker-Dialog)
- [Material wheel view](https://android-arsenal.com/details/1/5184)
- [WheelView](https://github.com/LukeDeighton/WheelView)
