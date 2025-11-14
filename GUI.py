import tkinter as tk
from tkinter import messagebox

def search_weather():
    city = city_entry.get()
    if not city:
        messagebox.showinfo("Weather App", "Please enter a city name.")
    else:
        # Replace with API results once both files are connected
        messagebox.showinfo("Weather Result", f"Fetching weather for: {city}")

# Main window of the GUI
root = tk.Tk()
root.title("Weather App")
root.geometry("350x180")

# Title of GUI
title_label = tk.Label(root, text="Weather Forecast", font=("Arial", 14, "bold"))
title_label.pack(pady=5)

# City text, we should make it case sensitve to prevent dummy searches, eq "Butt town"
city_label = tk.Label(root, text="Enter a city:")
city_label.pack(pady=(10, 0))

city_entry = tk.Entry(root, width=30)
city_entry.pack(pady=5)

# Search button
search_button = tk.Button(root, text="Search Weather", command=search_weather)
search_button.pack(pady=10)

# Quit button
quit_button = tk.Button(root, text="Quit", command=root.quit)
quit_button.pack(pady=5)

root.mainloop()