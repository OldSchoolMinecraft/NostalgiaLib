package net.oldschoolminecraft.nlib.update;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;

/**
 * NOT IMPLEMENTED
 *
 * Will allow plugin developers to check for updates, and (hopefully) apply them during runtime.
 */
public class UpdateManager
{
    private ArrayList<UpdatedPlugin> updatedPlugins;

    public void checkGit() { throw new NotImplementedException(); }
    public void checkHTTP() { throw new NotImplementedException(); }
    public void checkREST() { throw new NotImplementedException(); }

    public void register(UpdatedPlugin plugin)
    {
        throw new NotImplementedException();
    }
}
