$.timepicker.regional['ko'] = {
	timeOnlyTitle:'시간을 선택 해 주세요.',
	timeText: '시간',
	hourText: '시',
	minuteText: '분',
	secondText: '초',
	currentText: '현재시간',
	closeText: '닫기',
	appm: false,
	showTime: true,
	showSecond: true,
	time24h: true,
	timeFormat: 'hh:mm:ss'
};
$.timepicker.setDefaults($.timepicker.regional['ko']);

//Time Picker Option
var timePickerOption = {
	duration: '',
	showTime: true,
	showSecond: true,
	constrainInput: false,
	stepMinutes: 5,
	stepHours: 1,
	altTimeField: ''  
};