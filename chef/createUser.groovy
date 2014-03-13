def props = new Properties();
final def inputPropsFile = args[0];
final def inputPropsStream = null;

try {
	inputPropsStream = new FileInputStream(inputPropsFile);
	props.load(inputPropsStream);
} catch (IOException e) {
    throw new RuntimeException(e);
} finally {
	inputPropsStream.close();
}

final def name = props["name"]
final def fileName = props["fileName"]?:''
final def admin = props["admin"]?.toBoolean()
final def password = props["password"]?:''
final def userKey = props["userKey"]?:''

def sout = new StringBuffer()
def serr = new StringBuffer()
def adminCmd = ""
def fileNameCmd = ""
def passwordCmd = ""
def userKeyCmd = ""

if(admin){
	adminCmd = " -a"
}
if(fileName){
	fileNameCmd = " -f ${fileName}"
}
if(password){
	passwordCmd = " -p ${password}"
}
if(userKey){
	userKeyCmd = " --user-key ${userKey}"
}

def deployCommand = "knife user create ${name}${adminCmd}${fileName}${password}${userKey}"
def proc = deployCommand.execute()
proc.consumeProcessOutput(sout, serr)
proc.waitFor()

println "sout: ${sout}"
println "serr: ${serr}"

System.exit(0);
