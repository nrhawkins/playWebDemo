package edu.washington.nsre;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.washington.nsre.extraction.NewsSpikePredict;
import edu.washington.nsre.extraction.Tuple;

import edu.washington.nsre.figer.FigerParsedSentence;
import edu.washington.nsre.figer.ParseStanfordFigerReverb;
import edu.washington.nsre.figer.Parsed2Tuple;

import edu.washington.multir2.Argument;
import edu.washington.multir2.RelationPrediction;


public class NewsSpikeSentencePredict {

    String model_nsre = "model";

    public NewsSpikeSentencePredict() {}

    public String predict(String text) throws IOException, InterruptedException {

        List<Tuple> tuples = new ArrayList<Tuple>(); 

        List<FigerParsedSentence> ps = ParseStanfordFigerReverb.process(text);
        for(FigerParsedSentence fps : ps){
            System.out.println("NSSP FPS length: " + fps.len);        
            tuples.addAll(Parsed2Tuple.getReverbTuples(fps));
        }
        List<RelationPrediction> predictions = NewsSpikePredict.predict(model_nsre, tuples);
					
//	return predictions;
    
        System.out.println("NSSP text: " + text);
        System.out.println("NSSP FPS size: " + ps.size());
        System.out.println("NSSP tuples: " + tuples.size());        
        System.out.println("NSSP predictions size: " + predictions.size());
        String predictionString = "Confidence .. Relation .. Argument1 .. Argument2" + "\n\n";
        for(RelationPrediction prediction : predictions){
          System.out.println("NSSP prediction: " + prediction.getRelation() );
          predictionString = 
            predictionString + prediction.getConfidence()+" .. "+prediction.getRelation()+" .. "+prediction.getArg1().getArgName()+" .. "+prediction.getArg2().getArgName()+"\n";
        }

          return predictionString;
//        return Integer.toString(predictions.size());
//        return new StringBuilder(text).reverse().toString();
    }

}
