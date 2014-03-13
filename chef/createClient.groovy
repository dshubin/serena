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
final def admin = props["admin"]?.toBoolean()

def sout = new StringBuffer()
def serr = new StringBuffer()
def adminCmd = ""

if(admin){
	adminCmd = " -a"
}

def deployCommand = "knife client create ${name}${adminCmd}"
def proc = deployCommand.execute()
proc.consumeProcessOutput(sout, serr)
proc.waitFor()

println "sout: ${sout}"
println "serr: ${serr}"

System.exit(0);
