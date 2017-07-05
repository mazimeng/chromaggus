//package com.workasintended.ai.behavior;
//
//import com.badlogic.gdx.ai.btree.LeafTask;
//import com.badlogic.gdx.ai.btree.Task;
//import com.badlogic.gdx.math.Vector2;
//import com.workasintended.chromaggus.Unit;
//import com.workasintended.chromaggus.ai.behavior.Blackboard;
//import com.workasintended.chromaggus.ai.behavior.GetPosition;
//
///**
// * Created by mazimeng on 5/22/16.
// */
//public class WithinRadius extends LeafTask<Blackboard> {
//    private float radius = 32f;
//    private GetPosition getPosition;
//
//    public WithinRadius(GetPosition getPosition, float radius) {
//        this.getPosition = getPosition;
//        this.radius = radius;
//    }
//
//    @Override
//    public Status execute() {
//        Unit self = getObject().getSelf();
//        Vector2 station = getPosition.get(getObject());
//
//        float d2 = Vector2.dst2(station.x, station.y, self.getX(), self.getY());
//        System.out.println(String.format("checking radius, %s<=%s?, station(%s), current(%s, %s)", d2, radius*radius, station, self.getX(), self.getY()));
//        return d2 <= radius*radius?
//                Status.SUCCEEDED: Status.FAILED;
//    }
//
//    @Override
//    protected Task copyTo(Task task) {
//        return task;
//    }
//
//    @Override
//    public Task<Blackboard> cloneTask() {
//        return new WithinRadius(this.getPosition, this.radius);
//    }
//}
