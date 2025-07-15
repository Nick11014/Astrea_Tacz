import os
import re

def is_corrupted_comment(line):
    # Detecta comentários com caracteres não-ASCII
    return line.strip().startswith("//") and not all(ord(c) < 128 for c in line)

def process_file(filepath):
    with open(filepath, "r", encoding="utf-8") as f:
        lines = f.readlines()
    with open(filepath, "w", encoding="utf-8") as f:
        for line in lines:
            if not is_corrupted_comment(line):
                f.write(line)

def walk_and_process(root):
    for dirpath, _, filenames in os.walk(root):
        for filename in filenames:
            if filename.endswith(".java"):
                process_file(os.path.join(dirpath, filename))

if __name__ == "__main__":
    walk_and_process(".")
    print("Comentários corrompidos removidos de todos os arquivos .java.")