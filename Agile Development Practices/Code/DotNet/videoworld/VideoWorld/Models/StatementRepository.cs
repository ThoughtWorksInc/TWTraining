using System;
using System.Collections.Generic;
using System.Linq;

namespace VideoWorld.Models
{
    public class StatementRepository
    {
        readonly List<Statement> statements = new List<Statement>();

        public Statement FindById(int id)
        {
            return statements[id];
        }

        public int Add(Statement statement)
        {
            statements.Add(statement);
            return statements.Count - 1;
        }

        public IEnumerable<Statement> FindByCustomer(Customer customer)
        {
            return statements.Where(s => s.Customer == customer);
        }
    }
}