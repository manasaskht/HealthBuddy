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
    
    public partial class GroceryMaster
    {
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2214:DoNotCallOverridableMethodsInConstructors")]
        public GroceryMaster()
        {
            this.Groceries = new HashSet<Grocery>();
        }
    
        public int BarcodeID { get; set; }
        public string GroceryName { get; set; }
        public Nullable<decimal> CaloriePer100gm { get; set; }
    
        [System.Diagnostics.CodeAnalysis.SuppressMessage("Microsoft.Usage", "CA2227:CollectionPropertiesShouldBeReadOnly")]
        public virtual ICollection<Grocery> Groceries { get; set; }
    }
}
