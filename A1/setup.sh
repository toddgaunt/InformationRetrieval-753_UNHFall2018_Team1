# Script to run when first setting this maven project on a new computer

# Download the test data
if [ ! -e test200.flag ]; then
	mkdir -p data
	cd data/
	wget -nc http://trec-car.cs.unh.edu/datareleases/v2.0/test200.v2.0.tar.xz
	tar -xzvf test200.v2.0.tar.xz
	cd ..
	touch test200.flag
fi
