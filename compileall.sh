sh ./cleanall.sh
echo "compiling cblas"
cd cblas
sh ./compile.sh
cd ..
echo "compiling helloworld"
cd helloworld
sh ./compile.sh
cd ..
echo "compiling lapack"
cd lapack
sh ./compile.sh
cd ..
echo "compiling libclang"
cd libclang
sh ./compile.sh
cd ..
echo "compiling libcurl"
cd libcurl
sh ./compile.sh
cd ..
echo "compiling libgit2"
cd libgit2
sh ./compile.sh
cd ..
echo "compiling libjimage"
cd libjimage
sh ./compile.sh
cd ..
echo "compiling libproc"
cd libproc
sh ./compile.sh
cd ..
echo "compiling opengl"
cd opengl
sh ./compile.sh
cd ..
echo "compiling python"
cd python
sh ./compile.sh
cd ..
echo "compiling readline"
cd readline
sh ./compile.sh
cd ..
echo "compiling sqlite"
cd sqlite
sh ./compile.sh
cd ..
echo "compiling tensorflow"
cd tensorflow
sh ./compile.sh
cd ..
echo "compiling time"
cd time
sh ./compile.sh
cd ..