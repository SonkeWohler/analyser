package hyperDap.base.types.dataSet;

import java.util.ArrayList;

public class ValidityDataSet<T> extends DataSet<T> {

  private ArrayList<Boolean> valids;

  public ValidityDataSet(double base, double step) {
    super(base, step);
    this.valids = new ArrayList<Boolean>();
  }

  @Override
  public void add(int index, T value) {
    int i = this.values.size();
    super.add(index, value);
    if (i <= index) {
      while (i <= index) {
        this.valids.add(true);
        i++;
      }
    } else {
      this.valids.set(index, true);
    }
  }

  @Override
  public boolean add(T value) {
    if (super.add(value) == true) {
      if (this.valids.add(true) == true) {
        return true;
      }
      this.values.remove(this.values.size() - 1);
    }
    return false;
  }

  @Override
  public double addValue(T value) {
    this.valids.add(true);
    return super.addValue(value);
  }

  /**
   * Ensures that internally the validites and values align correctly. As the two properties are
   * stored in separate {@link ArrayList ArrayLists} this method will ensure they are of the same
   * length. If the validity list must be extended it is extended with {@code false}.
   * 
   * @category helper
   * @return {@code true} if changes had to be made, {@code false} otherwise.
   */
  public boolean cleanLength() {
    int a = this.values.size();
    int b = this.valids.size();
    if (a == b) {
      return false;
    }
    if (a > b) {
      for (int i = b; i < a; i++) {
        this.valids.add(false);
      }
      return true;
    }
    for (int i = a; i < b; i++) {
      this.valids.remove(a);
    }
    return true;
  }

  public boolean getValidByIndex(int index) {
    try {
      return this.valids.get(index);
    } catch (IndexOutOfBoundsException e) {
      return false;
    }
  }

  public boolean getValid(double independentValue) {
    return this.getValidByIndex(this.getIndex(independentValue));
  }

  public boolean getValid(Number independentValue) {
    return this.getValid(independentValue.doubleValue());
  }

  @Override
  public void clear() {
    super.clear();
    this.valids.clear();
  }

}
