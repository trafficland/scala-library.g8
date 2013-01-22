require 'rest_client'
require 'open-uri'

user = "$jenkins_user$"
password = "$jenkins_password$"
credentials = CGI::escape(user) + ":" + CGI::escape(password)
jenkins = "http://#{credentials}@build01.tl.com:8080"

['master', 'develop'].each do |branch|
  config = IO.read("./config-#{branch}.xml")
  job_name = "$name$-#{branch}"
  url = "#{jenkins}/createItem?name=#{job_name}"
  puts url
  response = RestClient.post url, config, :content_type => :xml, :accept => :xml
  unless response.code == 200
    puts response.to_str 
  else
    puts "Created job for #{job_name} successfully"
    
  end   

end  