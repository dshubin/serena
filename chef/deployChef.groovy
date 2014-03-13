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
    	
// @param deployCommand = knife ssh "nodeName" "sudo chef-client"
	
// name of the node to deploy on
final def query = props["query"];
final def sshLogin = props["sshLogin"]
final def sshPasswd = props["sshPasswd"]
def sudoPasswd = props["sudoPasswd"]

// If no sudo password given, attemt deploy with ssh password as sudo password
if (sudoPasswd == ""){
	sudoPasswd = sshPasswd
}

// command to be executed
final def deployCommand = "knife ssh ${query} sudo chef-client -x ${sshLogin} -P ${sshPasswd}";

def sout = new StringBuffer();
def serr = new StringBuffer();
def proc = deployCommand.execute();
proc.consumeProcessOutput(sout, serr);
proc.withWriter { writer ->
	writer << sudoPasswd
}

proc.waitFor();

println "sout: ${sout}"
println "serr: ${serr}"

System.exit(0);
    
