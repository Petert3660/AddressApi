set myDirName = ".\build\libs"

if exist myDirName (
    cd build\libs
    del *.*
    cd ..\..
)

call gradlew clean build copyFiles
call run
cd ..\..

