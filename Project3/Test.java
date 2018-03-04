public class Test<T extends State>{
  T initial;

  public Test(T i){
    initial = i;
    System.out.println(initial.getEstimateValue());
  }

  public<T extends State> T get(Class<T> type){
    return type.cast(initial);
  }
}
