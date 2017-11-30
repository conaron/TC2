// Prepare source somehow.
String source = "package test; public class Test { static { System.out.println(\"hello\"); } public Test() { System.out.println(\"world\"); } }";

// Save source in .java file.
File root = new File("/java"); // On Windows running on C:\, this is C:\java.
File sourceFile = new File(root, "test/Test.java");
sourceFile.getParentFile().mkdirs();
Files.write(sourceFile.toPath(), source.getBytes(StandardCharsets.UTF_8));

// Compile source file.
JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
compiler.run(null, null, null, sourceFile.getPath());

// Load and instantiate compiled class.
URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { root.toURI().toURL() });
Class<?> cls = Class.forName("test.Test", true, classLoader); // Should print "hello".
Object instance = cls.newInstance(); // Should print "world".
System.out.println(instance); // Should print "test.Test@hashcode".
