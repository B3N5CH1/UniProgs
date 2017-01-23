echo off

set filename=input.txt
set quantum=6
set contextSwitchTime=4

java -jar schedule_simulator.jar %filename% %quantum% %contextSwitchTime%

echo.
pause