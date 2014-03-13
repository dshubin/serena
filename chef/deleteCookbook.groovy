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
final def version = props["version"]
final def all = props["all"]
final def purge = props["purge"]

def sout = new StringBuffer();
def serr = new StringBuffer();
def allCmd = ""
def purgeCmd = ""

if(all){
	allCMD = " -a"
}
if(purge){
	purgeCMD = " -p"
}

def deployCommand = "knife cookbook delete ${name} ${version}${allCMD}${purgeCMD}"
def proc = deployCommand.execute();
proc.consumeProcessOutput(sout, serr);
proc.waitFor()

println "sout: ${sout}"
println "serr: ${serr}"

System.exit(0);
