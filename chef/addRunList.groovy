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
final def runList = props["runList"]
final def after = props["after"]

def sout = new StringBuffer();
def serr = new StringBuffer();
def afterCmd = ""

if(after){
afterCMD = " -a ${after}"
}

def deployCommand = "knife node run_list add ${name} ${runList} ${afterCMD}"
def proc = deployCommand.execute();
proc.consumeProcessOutput(sout, serr);
proc.waitFor()

println "sout: ${sout}"
println "serr: ${serr}"

System.exit(proc.exitValue());
