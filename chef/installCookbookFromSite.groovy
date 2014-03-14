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

// Required Field
final def cookbookName = props['cookbookName'];
// Optional Fields
final def cookbookVersion = props['cookbookVersion']?:'';
final def useCurrentBranch = props['useCurrentBranch']?.toBoolean();
final def defaultBranchName= props['defaultBranchName']?:'';
final def skipDependencies = props['skipDependencies']?.toBoolean();
final def path = props['path']?:'';

def sout = new StringBuffer();
def serr = new StringBuffer();

def command = "knife cookbook site install ${cookbookName}";

if(cookbookVersion){
	command = command + " ${cookbookVersion}";
}
if(useCurrentBranch){
	command = command + " --user-current-branch";
}
if(defaultBranchName){
	command = command + " --branch ${defaultBranchName}";
}
if(skipDependencies){
	command = command + " --skip-dependencies";
}
if(path){
	command = command + " --cookbook-path ${path}";
}

def proc = command.execute();
proc.consumeProcessOutput(sout, serr);
proc.waitFor();

println "sout: ${sout}";
println "serr: ${serr}";

System.exit(proc.exitValue());
