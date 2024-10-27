package com.google.mlkit.vision.demo.java.posedetector;

import java.lang.Math;

public class PoseComparison {
    
    int numPoses;
    public PoseComparison(){
        this.numPoses = 3;
    }

    static int error_bro=0;

    public void  set_error_bro(int n){
        error_bro=n;
    }
    public int get_error_bro(){
        return  error_bro;
    }

    /**
    * Function for comparing the pose.
    *
    * @param angleList Specify the angles of the various body parts in the
    *                   following order: left hand, right hand, left elbow, 
    *                   right elbow, left leg, right leg, left knee, right knee,
    *                   left ankle, right ankle. 
    * @return The errors between the given angles and the most similar angle of
    *         the defined poses.
    */
    public float[] comparePose(float angleList[]){
        float angleErrors[][], referenceAngles[][], 
               error,difference;
        int i, j, classifiedPoseIndex=0, totalError, minError=10000;
        angleErrors = new float[this.numPoses][10];
        referenceAngles = getReferenceeAngles();
//        for(i=0; i<referenceAngles.length; i++){
//            totalError = 0;
//            for(j=0; j<10; j++){
//                difference = 1 - (Math.abs(angleList[j] - referenceAngles[i][j])/referenceAngles[i][j]);
//                error = (float) (100/(1 +Math.pow(Math.E,((-Math.E)*difference+(Math.pow(Math.E,2))))));
//                angleErrors[i][j] = error;
//                totalError += error;
//            }
//            if((totalError < minError) | (minError == 0)){
//                minError = totalError;
//                classifiedPoseIndex = i;
//            }
//        }
        for (i = 0; i < referenceAngles.length; i++) {
            totalError = 0;
            for (j = 0; j < 10; j++) {
                // Avoid division by zero
                if (referenceAngles[i][j] == 0) {
                    continue; // or handle appropriately
                }
                difference = Math.abs(angleList[j] - referenceAngles[i][j]) / referenceAngles[i][j];
                error = (float)(difference * 100); // Error as a percentage
                angleErrors[i][j] = error;
                totalError += error;
            }
            if (totalError < minError) {
                minError = totalError;
                classifiedPoseIndex = i;
            }
        }

        set_error_bro(minError);

        return angleErrors[classifiedPoseIndex];
    }
    /**
    * INCOMPLETE FUNCTION. Function for getting the angles of defined poses.
    */
    private float[][] getReferenceeAngles(){
        float referenceAngles[][] = {{90.0f, 90.0f, 180.0f, 180.0f, 180.0f, 180.0f, 180.0f, 180.0f, 135.0f, 135.0f}, {165.0f, 165.0f, 165.0f, 165.0f, 104.0f, 104.0f, 170.0f, 170.0f, 125.0f, 125.0f}, {26.0f, 26.0f, 47.0f, 47.0f, 116.5f, 116.5f, 116.5f, 116.5f, 125.5f, 125.5f}};
//        float[][] extractedAngles = extractAnglesFromImages("/path/to/golden_poses");
        return referenceAngles;
    }

}
