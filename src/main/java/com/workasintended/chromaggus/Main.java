package com.workasintended.chromaggus;

import com.badlogic.gdx.ai.btree.BehaviorTree;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeLibrary;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeLibraryManager;
import com.badlogic.gdx.ai.btree.utils.BehaviorTreeParser;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.workasintended.chromaggus.ai.behavior.Blackboard;

/**
 * Created by mazimeng on 6/27/15.
 */
public class Main {
    public static void main(String[] arguments) throws IllegalAccessException, InstantiationException, ReflectionException {
    }

    public static void initBt() {
        BehaviorTreeLibraryManager libraryManager = BehaviorTreeLibraryManager.getInstance();
        BehaviorTreeLibrary library = new BehaviorTreeLibrary(BehaviorTreeParser.DEBUG_HIGH);
        libraryManager.setLibrary(library);

        {
            BehaviorTree<Blackboard> tree = new BehaviorTree<Blackboard>();
            library.registerArchetypeTree("test", tree);
        }
    }
}
