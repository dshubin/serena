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

final def name = props["Client"]

def sout = new StringBuffer();
def serr = new StringBuffer();

def deployCommand = "knife client delete ${name}"
def proc = deployCommand.execute();
proc.consumeProcessOutput(sout, serr);
proc.withWriter { writer ->
        writer << "y"
}
proc.waitFor()

println "sout: ${sout}"
println "serr: ${serr}"

System.exit(proc.exitValue());
