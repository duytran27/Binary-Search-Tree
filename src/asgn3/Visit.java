package asgn3;
/** Visit defines a method that 
 *  can be used in tree traversals. 
 * 
 *  Create an object that implements Visit. That 
 *  object has one method, visit that does whatever 
 *  you want to do when you visit a node. 
 * 
 *  The traversal methods in BST take an object of 
 *  type Visit and invoke the visit() method. 
 *  
 *  This is a way to pass methods as parameters in Java.
*/
public interface Visit<T>
{
   public void visit( T t); 
}
