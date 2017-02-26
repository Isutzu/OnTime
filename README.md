# App Name - *OnTime*

**OnTime** is a simple alarm application for Android.
At work I have 30min for taking lunch so I use this app to keep track of the time
The alarm is set up to fire by default 30min after the activation but this time can be changed
and use it as a general purpose alarm.



## Usage

* Just press the -- start lunch -- button and the alarm will fire up 30min after.
* A notification is received when the alarm is activated which can be stopped it by pressing the STOP ALARM
action button.


The following **additional** features will be implemented:

- [ ] Pick alarm sound or music.
- [x] The ability to change the default 30min time by using a TimePicker dialog
- [x] Adding a CountDownTimer
- [x] Adding CountDownTimer to Notification.
- [x] Adding seconds to CountDownTimer
- [ ] Implementation of material design
- [ ] Adding a shortcut to the tiles bar for a quick access
- [x] Improving user interface and user experience. We can get the same functionality by using only one button instead of   two(start/stop)


## Video Walkthrough


<img src='http://i.imgur.com/Oa4Opi9.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

For demonstration the alarm have been set to fire up after 10 seconds

GIF created with [LiceCap](http://www.cockos.com/licecap/).

### this is the current version of this branch

<img src='http://i.imgur.com/hf3RqDX.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

<img src='http://i.imgur.com/L2edNw3.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />





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
- [Start activity one](http://stackoverflow.com/questions/11153781/launch-an-activity-when-alarm-is-triggered)
- [Start Activity two](http://stackoverflow.com/questions/26454120/starting-activity-from-alarm-broadcast-receiver-even-when-user-clicked-home-butt)
- [Custom notification](https://www.laurivan.com/android-notifications-with-custom-layout/)
- [Custom notification](http://www.tutorialsface.com/2015/08/android-custom-notification-tutorial/)
- [Custom notification](http://www.androidauthority.com/create-powerful-notifications-708496/)
- [TimePicker](http://stackoverflow.com/questions/26906327/how-to-show-the-hours-only-using-timepicker-in-android)
## To Read
- [custom TimePicker Dialog. Adjusting the minutes in intervals](https://github.com/ziaagikian/Custom-Time-Picker-Dialog)
- [Material wheel view](https://android-arsenal.com/details/1/5184)
- [WheelView](https://github.com/LukeDeighton/WheelView)
