internal class Mylist
    {
        Employee[] _items = new Employee[2];
        public Mylist()
        {     _items[0] = new Employee { Salary = 6000, Name = "Raj" };
            _items[1] = new Employee { Salary = 3000, Name = "Mona" };

        }
        public Employee MyFind(Predicate<Employee> match) 
        { int size = _items.Length;
            for (int i = 0; i < size; i++)
            {
                if (match(_items[i]))
                    return _items[i];
            }
            return null;
        }  
    }