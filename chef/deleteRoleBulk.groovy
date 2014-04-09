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
//required
final def REGEX = props['deleteRoleREGEX'];
def sout = new StringBuffer();
def serr = new StringBuffer();


final def command = "knife role delete ${REGEX}";

def proc = command.execute();
proc.consumeProcessOutput(sout, serr);
proc.withWriter { writer ->
	writer << "y";
}
proc.waitFor();
println "sout: ${sout}";
println "serr: ${serr}";

System.exit(proc.exitValue());
