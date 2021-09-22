package registry;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import util.classutil.ClassUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Data
@RequiredArgsConstructor
public class ClazzRegistry {
    Map<Class<?>, Object> map = new HashMap<>();

    public Object get(Class<?> clazz) {
        return map.get(clazz);
    }

    public void set(Class<?> clazz) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        var obj = new ClassUtil().getBestConstructor(clazz).newInstance();
        map.put(clazz, obj);
    }

    public Set<Class<?>> getAllKeys() {
        return map.keySet();
    }


}
