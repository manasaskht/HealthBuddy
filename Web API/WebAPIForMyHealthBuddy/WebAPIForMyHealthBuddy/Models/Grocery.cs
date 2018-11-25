//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated from a template.
//
//     Manual changes to this file may cause unexpected behavior in your application.
//     Manual changes to this file will be overwritten if the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace WebAPIForMyHealthBuddy.Models
{
    using System;
    using System.Collections.Generic;
    
    public partial class Grocery
    {
        public int GroceryID { get; set; }
        public string GroceryName { get; set; }
        public Nullable<decimal> Calories { get; set; }
        public Nullable<System.DateTime> ExpiryDate { get; set; }
        public Nullable<int> Quantity { get; set; }
        public int GroceryMasterID { get; set; }
        public int UserID { get; set; }
    
        public virtual GroceryMaster GroceryMaster { get; set; }
        public virtual User User { get; set; }
    }
}