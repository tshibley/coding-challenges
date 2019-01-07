/*
servers   = primary,backup
primary   = 8888,http_port
backup    = 9999,http_port
http_port = 80
*/

public List<Integer> lookup(Map<String, List<String>> configs, String find) {
    List<Integer> result = new LinkedList<Integer>();
    helper(find, result, configs); 
    return result; 
    
} 

private void helper(String current, List<Integer> result, Map<String, List<String>> configs) {
    try {
        Integer value = Integer.parseInt(current); 
        result.add(value); 
    } catch (ex) {
        List<String> currentLookup = configs.get(current); 
        for(String s : currentLookup) {
            helper(s, result, configs); 
        }
    }
}
class Value

IntValue extends Value
Integer data; 

KeyValue extends Value
String key;

DictValue extends Value
Map<String, Value> dict; 

/*
servers   = { primary : { host : 8888, port : http_port },
              backup  : { host : 9999, port : http_port } }
ports     = { ssh : ssh_port, http : http_port }
ssh_port  = 22
http_port = 80
*/

Tree : Nodes (key : List values that are nodes )

public Value lookup (Map<String, Value> config, String key) {
    Value result = helper(config, key); 
    return result; 
}

private Value helper(Map<String, Value> config, String key) {
    Value data  = config.get(key);
    if(data instanceof IntValue) {
        return data; 
    } else if (data instanceof KeyValue) {
        return helper(config, config.get(data.key)); 
    } else {
        Value result = new DictValue(); 
        for(String key : data.dict.keySet()) {
            result.dict.put(key, helper(config, key); 
        }
        return result; 
    }
    
    
    
    
}