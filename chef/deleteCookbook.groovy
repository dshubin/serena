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
final def version = props["version"]?:''
final def all = props["all"]?.toBoolean()
final def purge = props["purge"]?.toBoolean()

def sout = new StringBuffer();
def serr = new StringBuffer();
def allCmd = ""
def purgeCmd = ""

if(all){
	allCmd = " -a"
}
if(purge){
	purgeCmd = " -p"
}

def deployCommand = "knife cookbook delete ${name} ${version}${allCmd}${purgeCmd}"
def proc = deployCommand.execute();
proc.consumeProcessOutput(sout, serr);
proc.withWriter { writer ->
        writer << "y"
}

proc.waitFor()

println "sout: ${sout}"
println "serr: ${serr}"

System.exit(proc.exitValue());
