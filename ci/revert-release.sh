export ALL_VERSIONS=$(echo $(awk -F '[<>]' '/version/{print $3}' pom.xml))

export VERSION=$(echo $ALL_VERSIONS | cut -d' ' -f2)

git tag -d ${VERSION}

git push --delete origin ${VERSION}