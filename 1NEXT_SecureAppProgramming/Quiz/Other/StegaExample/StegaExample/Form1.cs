using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;
using System.Data;
using System.IO;
using System.Text;
using System.Drawing.Imaging;
using System.Runtime.InteropServices;

namespace StegaExample
{
    public partial class Form1 : Form
    {
        FileStream bmpStream;
        public Form1()
        {
            InitializeComponent();
        }

        private void btnHide_Click(object sender, System.EventArgs e)
        {
            Bitmap oldBmp = new Bitmap(fileName);
            Bitmap bitmap = new Bitmap(oldBmp);

            Stream messageStream = GetMessageStream();
            if (messageStream.Length == 0)
            {
                MessageBox.Show("Please enter a message or select a file.");
                txtMessageText.Focus();
            }
            else
            {
                Stream keyStream = GetKeyStream(true);
                if (keyStream.Length == 0)
                {
                    MessageBox.Show("Please enter a password or select a key file.");
                    txtKeyText.Focus();
                }
                else
                {
                    try
                    {
                        SteganoGraphy.HideMessageInBitmap(messageStream, bitmap, keyStream, chkGrayscale.Checked);
                        picImage.Image = bitmap;
                        SaveFileTo32RGB(fileName, bitmap);
                    }
                    catch (Exception ex)
                    {
                        MessageBox.Show("Exception:\r\n" + ex.Message);
                    }
                }
                keyStream.Close();
            }
            messageStream.Close();
            bitmap = null;
        }

        private void btnExtract_Click(object sender, System.EventArgs e)
        {
            byte[] B = File.ReadAllBytes(fileName);
            GCHandle GCH = GCHandle.Alloc(B, GCHandleType.Pinned);
            IntPtr Scan0 = (IntPtr)((int)(GCH.AddrOfPinnedObject()) + 54);
            int W = Marshal.ReadInt32(Scan0, -36);
            int H = Marshal.ReadInt32(Scan0, -32);
            Bitmap bitmap = new Bitmap(W, H, 4 * W, PixelFormat.Format32bppArgb, Scan0);
            bitmap.RotateFlip(RotateFlipType.RotateNoneFlipY);
            GCH.Free();

            Stream messageStream = new MemoryStream();
            Stream keyStream = GetKeyStream(false);
            if (keyStream.Length == 0)
            {
                MessageBox.Show("Please enter a password or select a key file.");
                txtKeyText.Focus();
            }
            else
            {

                try
                {
                    SteganoGraphy.ExtractMessageFromBitmap(bitmap, keyStream, ref messageStream);
                    messageStream.Seek(0, SeekOrigin.Begin);
                    StreamReader reader = new StreamReader(messageStream, UnicodeEncoding.Unicode);
                    String readerContent = reader.ReadToEnd();
                    if (readerContent.Length > txtExtractedMsgText.MaxLength)
                    {
                        readerContent = readerContent.Substring(0, txtExtractedMsgText.MaxLength);
                    }
                    txtExtractedMsgText.Text = readerContent;
                    this.SuspendLayout();
                    picImageExtract.Image.Dispose();
                    picImageExtract.Image = null;
                }
                catch (Exception ex)
                {
                    MessageBox.Show("Exception:\r\n" + ex.Message);
                }
            }
            messageStream.Close();
            keyStream.Close();
            bitmap = null;
        }

        private Stream GetMessageStream()
        {
            Stream messageStream;
            byte[] messageBytes = UnicodeEncoding.Unicode.GetBytes(txtMessageText.Text);
            messageStream = new MemoryStream(messageBytes);
            return messageStream;
        }

        private Stream GetKeyStream(bool endec)
        {
            Stream keyStream;
            byte[] keyBytes;
            if (endec)
                keyBytes = UnicodeEncoding.Unicode.GetBytes(txtKeyText.Text);
            else
                keyBytes = UnicodeEncoding.Unicode.GetBytes(txtKeyTextExtract.Text);
            keyStream = new MemoryStream(keyBytes);
            return keyStream;
        }

        private void SetImage(String fileName, bool action)
        {
            if (action)
                picImage.Image = new Bitmap(fileName);
            //else
            //    \\picImageExtract.Image = new Bitmap(fileName);
            btnHide.Enabled = btnExtract.Enabled = true;
        }

        private String GetFileName(String filter)
        {
            OpenFileDialog dlg = new OpenFileDialog();
            dlg.Multiselect = false;
            if (filter.Length > 0) { dlg.Filter = filter; }

            if (dlg.ShowDialog(this) != DialogResult.Cancel)
            {
                return dlg.FileName;
            }
            else
            {
                return null;
            }
            dlg.Dispose();
        }

        private void btnImageFile_Click(object sender, System.EventArgs e)
        {
            fileName = GetFileName("BitmapFile (*.bmp)|*.bmp");
            if (fileName != null)
            {
                if (hiex)
                    txtImageFile.Text = fileName;
                else
                    txtImageFileExtract.Text = fileName;
                SetImage(fileName, hiex);
            }
        }

        private void SaveFileTo32RGB(string file, Bitmap bitmap)
        {
            this.SuspendLayout();
            //picImage.Image.Dispose();
            //picImage.Image = null;
            btnHide.Enabled = btnExtract.Enabled = false;
            txtImageFile.Text = String.Empty;
            this.ResumeLayout();
            char[] trimChars = { '.', 'b', 'm', 'p' };
            string fileShort = fileName.TrimEnd(trimChars);
            //transforming 32 in 24 color bits image
            //Bitmap bitmap = new Bitmap(img.Width, img.Height, System.Drawing.Imaging.PixelFormat.Format32bppArgb);
            //Bitmap bitmap = new Bitmap(img.Width, img.Height, PixelFormat.Format32bppArgb);
            //for (int i = 0; i < img.Width; i++)
            //{
            //    for (int j = 0; j < img.Height; j++)
            //    {
            //        Color temp = img.GetPixel(i, j);
            //        bitmap.SetPixel(i, j, temp);
            //    }
            //} //low performance
            bitmap.Save(fileShort + "_encoded.bmp", System.Drawing.Imaging.ImageFormat.Bmp);
        }

        private void txtImageFile_KeyDown(object sender, System.Windows.Forms.KeyEventArgs e)
        {
            if (e.KeyCode == Keys.Enter)
            {
                SetImage(txtImageFile.Text, true);
            }
        }

        private void tabControl2_Selecting(object sender, TabControlCancelEventArgs e)
        {
            if (tabControl2.SelectedTab.Text == "Encoding")
                hiex = true;
            else
                hiex = false;
            if (tabControl2.SelectedTab.Text == "Decoding")
            {
                txtExtractedMsgText.Text = "";
                txtKeyTextExtract.Text = "";
            }
        }

        private void hideToolStripMenuItem_Click(object sender, EventArgs e)
        {
            tabControl2.SelectedIndex = 0;
            hiex = true;
        }

        private void extractToolStripMenuItem_Click(object sender, EventArgs e)
        {
            tabControl2.SelectedIndex = 1;
            hiex = false;
        }

        private void exitToolStripMenuItem_Click(object sender, EventArgs e)
        {
            Application.Exit();
        }
    }
}
