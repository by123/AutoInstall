# 输入的包名
name=$1
# $1 就是传进来的第一个参数 $2就是第二个参数

#echo 是打印的意思

echo "---ahh---"

echo ${name}

appName="${name}.app"

echo ${appName}

plistBuddy="/usr/libexec/PlistBuddy"

configName="Payload/${appName}/DChannel.plist"

configNameNew="Payload"

ipa="${name}.ipa"

# 打好包后输出的文件夹名字

outUpdateAppDir="OutApps"

# 获取当前目录，并切换过去

currDir=${PWD}

echo ${currDir}

cd "${currDir}"

echo "-----${currDir}"

# 生成日志目录

#mkdir log

rm -rf Payload

#解压缩

unzip -o -q ${ipa} #>> log/unzipUpdateApp.log

#echo `ls Payload`

# 删除旧的文件，重新生成

rm -rf "${outUpdateAppDir}"

mkdir "${outUpdateAppDir}"

echo "------------------------开始打包程序------------------------"

#echo ""

# 渠道列表文件开始打包

#for line in $(cat TargetChanelList.txt)

#循环数组,批量打包时需要修改的渠道号和渠道Id  ("1174" "1173")
# 这里的参数可以从外面直接传过来,
ChannelID=("scrat1" "scrat2" "scrat3" "scrat4" "scrat5")

echo ${#ChannelID[@]}

for ((i=0;i<${#ChannelID[@]};i++))

do

#echo是输出命令,可以忽略

echo "........正在打包渠道号:${ChannelID[$i]}"

#    cd Payload/${appName}

# 设置Channel.plist

echo "-----1----${PWD}"

#修改

$plistBuddy -c "set :Channel ${ChannelID[$i]}" ${configName}

echo "-----2----${PWD}"

rm -rf Payload/${appName}/_CodeSignature

# ipa包签名： codesign -f -s "by" --entitlements "Entitlements.plist" "Payload/${appName}"

codesign -f -s "jianjun qiu" --entitlements entitlements.plist Payload/${appName}

#Payload/Demo.app: replacing existing signature ：这个时候是已经OK

zip -rq "${outUpdateAppDir}/${ChannelID[$i]}.ipa" "Payload"

echo "........打包已完成"

done

echo "------------------------程序打包已结束------------------------"