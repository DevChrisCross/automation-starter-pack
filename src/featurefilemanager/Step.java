/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package featurefilemanager;

/**
 *
 * @author Chris
 */
public class Step {
    private String keyword;
    private String definition;

    public Step(String keyword, String definition) {
        this.keyword = keyword;
        this.definition = definition;
    }

    public String getKeyword(){
        return this.keyword;
    }

    public String getDefinition(){
        return this.definition;
    }
}
