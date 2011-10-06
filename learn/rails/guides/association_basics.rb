class Customer < ActiveRecord :: Base
	has_many :orders, :dependent => :destroy
end

class Order < ActiveRecord :: Base
	belongs_to :customer
end

#create a new order for a particular customer
@order = @customer.orders.create(:order_date => Time.now)

#deleteing a customer and all of its orders is much easier
@customer.destroy

# Types of Associations
# Rails supports six types of association:
types = %w[
belongs_to
has_one
has_many
has_many :through
has_one :through
has_and_belongs_to_many
]

# The belongs_to Association
# sets up a one-to-one connection with another model
class Order < ActiveRecord :: Base
	belongs_to :customer
end

# The has_one Association
# A has_one association also sets up a one-to-one connection with another model
class Supplier < ActiveRecord :: Base
	has_one  :account
end
=begin
suppliers:
	id: integer
	name:string

Accounts:
	id:integer
	supplier_id:integer
	account_number:string
=end

# The has_many association
# A has_many association indicates a one-to-many with another model.
# The association indicates that each instance of the model has zero or more instance of another model.
class Customer < ActvieRecord :: Base
	has_many :orders
end
=begin
customers:
	id:integer
	name:string

orders:
	id:integer
	customer_id:integer
	order_date:datetime

=end

#The has_many :through Association
#A has_many :through association is often used to set up a many-to-many connection with another model.
#This association indicates that the declaring model can be matched  with zero or more instances of another model by proceeding through a third model.
class Physician < ActiveRecord :: Base
	has_many :appointments
	has_many :patients, :through => :appointments
end

class Appointment < ActiveRecord :: Base
	belongs_to :physician
	belongs_to :patient
end

class Patient < ActiveRecord :: Base
	has_many :appointments
	has_many :pysicians, :through => :appointments
end
=begin
physicians:
	id:integer
	name:string
appointments:
	id:integer
	physician_id:integer
	patient_id:integer
	appointment_date:datetime
patients:
	id:integer
	name:string

=end

#The has_one :through Association
class Supplier < ActiveRecord :: Base
	has_one :account
	has_one :account_history, :through => :account
end

class Account < ActiveRecord :: Base
	belongs_to :supplier
	has_one :account_history
end

class AccountHistory < ActiveRecord :: Base
	belongs_to :account
end

# The has_and_belongs_to_many Association
# A has_and_belongs_to_many association creates a direct many-to-many connection with another model,
# with no intervening model, if your application includes assemblies and parts, with each assembly having many parts and each part appearing in many assemblies, you could 
# declare the models this way:
class Assembly < ActiveRecord :: Base
	has_and_belongs_to_many :parts
end

class Part < ActiveRecord :: Base
	has_and_belongs_to_many :assemblies
end

=begin
assemblies:
	id:integer
	name:string
parts:
	id:integer
	part_number:string
assemblies_parts
	assembly_id:integer
	part_id:integer
=end

#Choosing Between belongs_to and has_one

#Polymorphic Associations
#A slighty more advanced twist on assiciation is the polymorphic association .
#With polymorphic associations, a model can belong to more than one other model ,
#on a single association . For example, you might have a picture model that belgongs to either an employee or a product model.
class Picture < ActiveRecord :: Base
	belongs_to :imageable, :polymorphic =>true
end

class Employee < ActiveRecord :: Base
	has_many :pictures, :as => :imageable
end

class Product < ActiveRecord :: Base
	has_many :pictures, :as => :imageable
end

class CreatePictures < ActiveRecord :: Migration
	def self.up
		create_table :pictures do |t|
			t.string :name
			t.integer :imageable_id
			t.string :imageable_type
			t.timestamps
		end
	end

	def self.down
		drop_table :pictures
	end
end
#This migration can be simplified by using the t.references form:
class CreatePictures < ActiveRecord::Migration
	def self.up
		create_table :pictures do |t|
			t.string :name
			t.references :imageable, :polymorphic => true
			t.timestamps
		end
	end

	def self.down
		drop_table :pictures
	end
end

# Self Joins
# In designing a data model, you will sometimes find a model that should have a relation to itself. 
# For example, you may want to store all employees in a single database model, but be able to trace relationships such as between manager and subordinates.
# This situation can be modeled with self-joining associations:
class Employee < ActiveRecord :: Base
	has_many :subordinates, :class_name =>"Employee"
	belongs_to :manager, :class_name => "Employee"
end

class Assembly < ActiveRecord :: Base
	has_and_belongs_to_many :parts
end 
class Part < ActiveRecord :: Base
	has_and_belongs_to_many :asemblies
end

class CreateAssemblyPartJoinTable < ActiveRecord::Migration
	create_table :assemblies_parts, :id => false do |t|
		t.integer :assembly_id
		t.integer :part_id
	end
	def self.down
		drop_table :assemblies_parts
	end
end
