validates_before_save_to_database = %w[create
create!
save
save!
update
update_attributes
update_attributes!]

#save(:validate => false)

skip_validates_methods = %w[
decrement!
decrement_counter
increment!
increment_counter
toggle!
update_all
update_attribute
update_counters
]

class Person < ActiveRecord::Base
  validates_presence_of :name
end
 
Person.create(:name => "John Doe").valid? # => true
Person.create(:name => nil).valid? # => false

=begin
class Person < ActiveRecord::Base
  validates_presence_of :name
end
 
>> p = Person.new
=> #<Person id: nil, name: nil>
>> p.errors
=> {}
 
>> p.valid?
=> false
>> p.errors
=> {:name=>["can't be blank"]}
 
>> p = Person.create
=> #<Person id: nil, name: nil>
>> p.errors
=> {:name=>["can't be blank"]}
 
>> p.save
=> false
 
>> p.save!
=> ActiveRecord::RecordInvalid: Validation failed: Name can't be blank
 
>> Person.create!
=> ActiveRecord::RecordInvalid: Validation failed: Name can't be blank

=end

#errors[]
errors[:attribute]

class Person < ActiveRecord::Base
  validates_presence_of :name
end
 
#>> Person.new.errors[:name].any? # => false
#>> Person.create.errors[:name].any? # => true

#validates_acceptance_of
class Person < ActiveRecord::Base
  validates_acceptance_of :terms_of_service
end
class Person < ActiveRecord::Base
  validates_acceptance_of :terms_of_service, :accept => 'yes'
end

#validates_associated
#You should use this helper when your model has associations with other models and they also need to be validated. When you try to save your object, valid? will be called upon each one of the associated objects.
class Library < ActiveRecord::Base
  has_many :books
  validates_associated :books
end

#validates_confirmation_of
#You should use this helper when you have two text fields that should receive exactly the same content
# For example, you may want to confirm an email address or a password. This validation creates a virtual attribute whose name is the name of the field that has to be confirmed with “_confirmation” appended
class Person < ActiveRecord::Base
  validates_confirmation_of :email
  validates_presence_of :email_confirmation
end
#<%= text_field :person, :email %>
#<%= text_field :person, :email_confirmation %>

#validates_exclusion_of
class Account < ActiveRecord::Base
  validates_exclusion_of :subdomain, :in => %w(www),
    :message => "Subdomain %{value} is reserved."
end

#validates_format_of
#This helper validates the attributes’ values by testing whether they match a given regular expression, which is specified using the :with option.
class Product < ActiveRecord::Base
  validates_format_of :legacy_code, :with => /\A[a-zA-Z]+\z/,
    :message => "Only letters allowed"
end

#validates_inclusion_of
#This helper validates that the attributes’ values are included in a given set. In fact, this set can be any enumerable object.
class Coffee < ActiveRecord::Base
  validates_inclusion_of :size, :in => %w(small medium large),
    :message => "%{value} is not a valid size"
end

#validates_length_of
# :wrong_length, :too_long, and :too_short options and %{count} as a placeholder for the number corresponding to the length constraint being used
class Person < ActiveRecord::Base
  validates_length_of :name, :minimum => 2
  validates_length_of :bio, :maximum => 500
  validates_length_of :password, :in => 6..20
  validates_length_of :registration_number, :is => 6
end

class Person < ActiveRecord::Base
  validates_length_of :bio, :maximum => 1000,
    :too_long => "%{count} characters is the maximum allowed"
end

#This helper counts characters by default, but you can split the value in a different way using the :tokenizer option:
class Essay < ActiveRecord::Base
  validates_length_of :content,
    :minimum   => 300,
    :maximum   => 400,
    :tokenizer => lambda { |str| str.scan(/\w+/) },
    :too_short => "must have at least %{count} words",
    :too_long  => "must have at most %{count} words"
end

#validates_numericality_of
class Player < ActiveRecord::Base
  validates_numericality_of :points
  validates_numericality_of :games_played, :only_integer => true
end

=begin
:greater_than – Specifies the value must be greater than the supplied value. The default error message for this option is “must be greater than %{count}”.
:greater_than_or_equal_to – Specifies the value must be greater than or equal to the supplied value. The default error message for this option is “must be greater than or equal to %{count}”.
:equal_to – Specifies the value must be equal to the supplied value. The default error message for this option is “must be equal to %{count}”.
:less_than – Specifies the value must be less than the supplied value. The default error message for this option is “must be less than %{count}”.
:less_than_or_equal_to – Specifies the value must be less than or equal the supplied value. The default error message for this option is “must be less than or equal to %{count}”.
:odd – Specifies the value must be an odd number if set to true. The default error message for this option is “must be odd”.
:even – Specifies the value must be an even number if set to true. The default error message for this option is “must be even”.
=end

#validates_presence_of
#This helper validates that the specified attributes are not empty. It uses the blank? method to check if the value is either nil or a blank string, that is, a string that is either empty or consists of whitespace.
class Person < ActiveRecord::Base
  validates_presence_of :name, :login, :email
end
class LineItem < ActiveRecord::Base
  belongs_to :order
  validates_presence_of :order_id
end

#validates_uniqueness_of
class Account < ActiveRecord::Base
  validates_uniqueness_of :email
end

class Holiday < ActiveRecord::Base
  validates_uniqueness_of :name, :scope => :year,
    :message => "should happen once per year"
end
class Person < ActiveRecord::Base
  validates_uniqueness_of :name, :case_sensitive => false
end

#validates_with
#This helper passes the record to a separate class for validation.
class Person < ActiveRecord::Base
  validates_with GoodnessValidator
end
 
class GoodnessValidator < ActiveModel::Validator
  def validate
    if record.first_name == "Evil"
      record.errors[:base] << "This person is evil"
    end
  end
end

class Person < ActiveRecord::Base
  validates_with GoodnessValidator, :fields => [:first_name, :last_name]
end
 
class GoodnessValidator < ActiveRecord::Validator
  def validate
    if options[:fields].any?{|field| record.send(field) == "Evil" }
      record.errors[:base] << "This person is evil"
    end
  end
end

# validates_each
#This helper validates attributes against a block. It doesn’t have a predefined validation function. You should create one using a block, and every attribute passed to validates_each will be tested against it. In the following example, we don’t want names and surnames to begin with lower case.
class Person < ActiveRecord::Base
  validates_each :name, :surname do |model, attr, value|
    model.errors.add(attr, 'must start with upper case') if value =~ /\A[a-z]/
  end
end

##Common Validation Options
#:allow_nil
class Coffee < ActiveRecord::Base
  validates_inclusion_of :size, :in => %w(small medium large),
    :message => "%{value} is not a valid size", :allow_nil => true
end

# :allow_blank
class Topic < ActiveRecord::Base
  validates_length_of :title, :is => 5, :allow_blank => true
end
 
Topic.create("title" => "").valid? # => true
Topic.create("title" => nil).valid? # => true

#:message

#:on
#The :on option lets you specify when the validation should happen. The default behavior for all the built-in validation helpers is to be run on save (both when you’re creating a new record and when you’re updating it). If you want to change it, you can use :on => :create to run the validation only when a new record is created or :on => :update to run the validation only when a record is updated.
class Person < ActiveRecord::Base
  # it will be possible to update email with a duplicated value
  validates_uniqueness_of :email, :on => :create
 
  # it will be possible to create the record with a non-numerical age
  validates_numericality_of :age, :on => :update
 
  # the default (validates on both create and update)
  validates_presence_of :name, :on => :save
end

##Conditional Validation
#Using a Symbol with :if and :unless
class Order < ActiveRecord::Base
  validates_presence_of :card_number, :if => :paid_with_card?
 
  def paid_with_card?
    payment_type == "card"
  end
end

#Using a String with :if and :unless
class Person < ActiveRecord::Base
  validates_presence_of :surname, :if => "name.nil?"
end

#Using a Proc with :if and :unless
class Account < ActiveRecord::Base
  validates_confirmation_of :password,
    :unless => Proc.new { |a| a.password.blank? }
end

#Creating Custom Validation Methods
class Invoice < ActiveRecord::Base
  validate :expiration_date_cannot_be_in_the_past,
    :discount_cannot_be_greater_than_total_value
 
  def expiration_date_cannot_be_in_the_past
    errors.add(:expiration_date, "can't be in the past") if
      !expiration_date.blank? and expiration_date < Date.today
  end
 
  def discount_cannot_be_greater_than_total_value
    errors.add(:discount, "can't be greater than total value") if
      discount > total_value
  end
end


ActiveRecord::Base.class_eval do
  def self.validates_as_choice(attr_name, n, options={})
    validates_inclusion_of attr_name, {:in => 1..n}.merge(options)
  end
end

## errors
class Person < ActiveRecord::Base
  validates_presence_of :name
  validates_length_of :name, :minimum => 3
end
 
person = Person.new
person.valid? # => false
person.errors
 # => {:name => ["can't be blank", "is too short (minimum is 3 characters)"]}
 
person = Person.new(:name => "John Doe")
person.valid? # => true
person.errors # => []

class Person < ActiveRecord::Base
  validates_presence_of :name
  validates_length_of :name, :minimum => 3
end
 
person = Person.new(:name => "John Doe")
person.valid? # => true
person.errors[:name] # => []
 
person = Person.new(:name => "JD")
person.valid? # => false
person.errors[:name] # => ["is too short (minimum is 3 characters)"]
 
person = Person.new
person.valid? # => false
person.errors[:name]
 # => ["can't be blank", "is too short (minimum is 3 characters)"]

 #errors.add
 class Person < ActiveRecord::Base
  def a_method_used_for_validation_purposes
    errors.add(:name, "cannot contain the characters !@#%*()_-+=")
  end
end
 
person = Person.create(:name => "!@#")
 
person.errors[:name]
 # => ["cannot contain the characters !@#%*()_-+="]
 
person.errors.full_messages
 # => ["Name cannot contain the characters !@#%*()_-+="]

 class Person < ActiveRecord::Base
    def a_method_used_for_validation_purposes
      errors[:name] = "cannot contain the characters !@#%*()_-+="
    end
  end
 
  person = Person.create(:name => "!@#")
 
  person.errors[:name]
   # => ["cannot contain the characters !@#%*()_-+="]
 
  person.errors.to_a
   # => ["Name cannot contain the characters !@#%*()_-+="]

   # errors[:base]
   class Person < ActiveRecord::Base
  def a_method_used_for_validation_purposes
    errors[:base] << "This person is invalid because ..."
  end
end

#errors.clear
class Person < ActiveRecord::Base
  validates_presence_of :name
  validates_length_of :name, :minimum => 3
end
 
person = Person.new
person.valid? # => false
person.errors[:name]
 # => ["can't be blank", "is too short (minimum is 3 characters)"]
 
person.errors.clear
person.errors.empty? # => true
 
p.save # => false
 
p.errors[:name]
 # => ["can't be blank", "is too short (minimum is 3 characters)"]

 # errors.size
 class Person < ActiveRecord::Base
  validates_presence_of :name
  validates_length_of   :name, :minimum => 3
  validates_presence_of :email
end
 
person = Person.new
person.valid? # => false
person.errors.size # => 3
 
person = Person.new(:name => "Andrea", :email => "andrea@example.com")
person.valid? # => true
person.errors.size # => 0


## Displaying Validation Errors in the View
#error_messages and error_messages_for
class Product < ActiveRecord::Base
  validates_presence_of :description, :value
  validates_numericality_of :value, :allow_nil => true
end

=begin
<%= form_for(@product) do |f| %>
  <%= f.error_messages %>
  <p>
    <%= f.label :description %><br />
    <%= f.text_field :description %>
  </p>
  <p>
    <%= f.label :value %><br />
    <%= f.text_field :value %>
  </p>
  <p>
    <%= f.submit "Create" %>
  </p>
<% end %>

<%= error_messages_for :product %>


<%= f.error_messages :header_message => "Invalid product!",
  :message => "You'll need to fix the following fields:",
  :header_tag => :h3 %>

=end

##Callbacks Overview
class User < ActiveRecord::Base
  validates_presence_of :login, :email
 
  before_validation :ensure_login_has_a_value
 
  protected
  def ensure_login_has_a_value
    if login.nil?
      self.login = email unless email.blank?
    end
  end
end

class User < ActiveRecord::Base
  validates_presence_of :login, :email
 
  before_create {|user| user.name = user.login.capitalize
    if user.name.blank?}
end

##Available Callbacks
=begin
10.1 Creating an Object
before_validation
after_validation
before_save
after_save
before_create
around_create
after_create

10.2 Updating an Object
before_validation
after_validation
before_save
after_save
before_update
around_update
after_update

10.3 Destroying an Object
before_destroy
after_destroy
around_destroy


after_initialize and after_find

=end

class User < ActiveRecord::Base
  def after_initialize
    puts "You have initialized an object!"
  end
 
  def after_find
    puts "You have found an object!"
  end
end

=begin
>> User.new
You have initialized an object!
=> #<User id: nil>
 
>> User.first
You have found an object!
You have initialized an object!
=> #<User id: 1>
=end


##Running Callbacks
#The following methods trigger callbacks:
=begin
create
create!
decrement!
destroy
destroy_all
increment!
save
save!
save(false)
toggle!
update
update_attribute
update_attributes
update_attributes!
valid?

=end

#Additionally, the after_find callback is triggered by the following finder methods:
=begin
all
first
find
find_all_by_attribute
find_by_attribute
find_by_attribute!
last
=end

##Skipping Callbacks
=begin
decrement
decrement_counter
delete
delete_all
find_by_sql
increment
increment_counter
toggle
update_all
update_counters

=end


##Relational Callbacks
class User < ActiveRecord::Base
  has_many :posts, :dependent => :destroy
end
 
class Post < ActiveRecord::Base
  after_destroy :log_destroy_action
 
  def log_destroy_action
    puts 'Post destroyed'
  end
end

= begin
>> user = User.first
=> #<User id: 1>
>> user.posts.create!
=> #<Post id: 1, user_id: 1>
>> user.destroy
Post destroyed
=> #<User id: 1>

=end

##Conditional Callbacks
class Order < ActiveRecord::Base
  before_save :normalize_card_number, :if => :paid_with_card?
end

class Order < ActiveRecord::Base
  before_save :normalize_card_number, :if => "paid_with_card?"
end

class Order < ActiveRecord::Base
  before_save :normalize_card_number,
    :if => Proc.new { |order| order.paid_with_card? }
end

class Comment < ActiveRecord::Base
  after_create :send_email_to_author, :if => :author_wants_emails?,
    :unless => Proc.new { |comment| comment.post.ignore_comments? }
end

#Callback Classes
class PictureFileCallbacks
  def after_destroy(picture_file)
    File.delete(picture_file.filepath)
      if File.exists?(picture_file.filepath)
  end
end
class PictureFile < ActiveRecord::Base
  after_destroy PictureFileCallbacks.new
end

class PictureFileCallbacks
  def self.after_destroy(picture_file)
    File.delete(picture_file.filepath)
      if File.exists?(picture_file.filepath)
  end
end

class PictureFile < ActiveRecord::Base
  after_destroy PictureFileCallbacks
end


## Observers
#rails generate observer User

class UserObserver < ActiveRecord::Observer
  def after_create(model)
    # code to send confirmation email...
  end
end

#Registering Observers
# Activate observers that should always be running
config.active_record.observers = :user_observer

#Sharing Observers
class MailerObserver < ActiveRecord::Observer
  observe :registration, :user
 
  def after_create(model)
    # code to send confirmation email...
  end
end

