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
final def contents = props["contents"]
final def overwrite = props["overwrite"]?.toBoolean()


try {
    def file = new File(name).canonicalFile
    if (file.exists() && !overwrite) {
        println "File ${file} already exists!"
        System.exit(1);
    }
    else {
        file.write(contents)
        println "Successfully ${overwrite?'replaced':'created'} file ${file} with contents:"
        println contents
    }
}
catch (Exception e) {
    println "Error creating file ${name}: ${e.message}"
    System.exit(1)
}
System.exit(0);
