
# 광주
run -i ../testimage/img1.jpg -h 192.168.0.190 -p 7777 -r 101 -a 1
run -i ../testimage/img2.jpg -h 192.168.0.190 -p 7777 -r 101 -a 1

# 포항
run -i ../testimage/img1.jpg -h 192.168.0.190 -p 7777 -r 101 -a 2
run -i ../testimage/img2.jpg -h 192.168.0.190 -p 7777 -r 101 -a 2



# 광주
run -i ../testimage/img1.jpg -h 127.0.0.1 -p 7777 -r 101 -a 1
run -i ../testimage/img2.jpg -h 127.0.0.1 -p 7777 -r 101 -a 1

# 포항
run -i ../testimage/img1.jpg -h 127.0.0.1 -p 7777 -r 101 -a 2
run -i ../testimage/img2.jpg -h 127.0.0.1 -p 7777 -r 101 -a 2



#로그설정.
http://175.197.51.165:52000/monitoring/test/config 
http://175.197.51.165:52000/monitoring/test/config?type=udp&val=true
http://175.197.51.165:52000/monitoring/test/config?type=event&val=true
http://175.197.51.165:52000/monitoring/test/config?type=stat&val=true

# 입력 확인. 

http://175.197.51.165:52000/monitoring/agentif/stat

http://175.197.51.165:52000/monitoring/agentif/event


# 맵확인. 
http://175.197.51.165:52000/monitoring/test-map


