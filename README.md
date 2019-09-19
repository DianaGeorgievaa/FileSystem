# FileSystem

Commands:

cd <location>
  
Change current directory

mkdir <dir_name>

Creates new directory in the current location

create_file <file_name>

Creates an empty file in the current location

cat <file_name>

Displays the file content

write <file_name> <overwrite_option> <line_number> <line_contents>

Writes the content into the file onto the given line

ls

Lists the files in the current directory

ls --sorted

Lists the files in the current directory in ascending/descending order, sorted by size

wc 

Counts number of words and lines

wc <file_name>

Counts number of words in the given file

wc <text>
  
Counts number of words in the given text

wc -l <file_name>

Counts number of lines in the given file

wc -l <text>
  
Counts number of lines in the given text

rm <file_name>

Deletes file with given name (Makes it not accessible for other commands and only if a new file is created on top of that memory, then the file contents are written on top)

remove <file_name> <line_number1> <line_number2>

Removes from file lines from the first one inclusively to the second one exclusively

Specifications

File size

file_size = number_of_lines + nuber_of_characters
Pipe

Implemented pipeline functionality - an output of a command to be the input of the next

Virtual and real file system

Two file system implementations are supported - real and virtual. The user can choose one of them.
