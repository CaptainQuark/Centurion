package centurion.model.playground;

/**
 * Unfinished generic type converter.
 *
 * @author Thomas Schönmann
 * @version %I%
 */
class Converter<T> {
    private Class<T> convertTo;

    public Converter(Class<T> convertTo){
        this.convertTo = convertTo;
    }

    @SuppressWarnings("unchecked")
    public <V> T convert(V from){

        if(from instanceof String){
            if(convertTo.getSimpleName().equals("Integer"))
                return (T) (Integer) Integer.parseInt((String) from);
        }

        return null;
    }
}
