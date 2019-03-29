TYPE=TRIGGERS
triggers='CREATE DEFINER=`sid`@`localhost` TRIGGER update_surgery_duration after UPDATE ON surgery\n   FOR EACH ROW\n   update patient p inner join surgery s on p.surgery_type = \n   s.surgery_type set p.surgery_duration = s.surgery_duration'
sql_modes=1073741824
definers='sid@localhost'
client_cs_names='utf8'
connection_cl_names='utf8_general_ci'
db_cl_names='latin1_swedish_ci'
